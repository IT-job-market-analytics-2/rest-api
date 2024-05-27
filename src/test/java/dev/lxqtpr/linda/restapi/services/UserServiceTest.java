package dev.lxqtpr.linda.restapi.services;

import dev.lxqtpr.linda.restapi.authentication.CustomUserDetails;
import dev.lxqtpr.linda.restapi.core.exceptions.ResourceNotFoundException;
import dev.lxqtpr.linda.restapi.doman.user.User;
import dev.lxqtpr.linda.restapi.doman.user.UserRepository;
import dev.lxqtpr.linda.restapi.doman.user.UserService;
import dev.lxqtpr.linda.restapi.doman.user.dto.EditUserDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Spy
    private static ModelMapper modelMapper = new ModelMapper();

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeAll
    static void prepare(){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(PRIVATE);
    }

    private static List<User> users = List.of(
            new User(1l,"test2","123",123l),
            new User(2l,"test3","123",123l),
            new User(3l,"test4","123",123l)
    );
    private static User user = User
            .builder()
            .id(1l)
            .telegramChatId(123l)
            .username("test")
            .password("test")
            .build();
    private static EditUserDto updateUserDto = new EditUserDto(1255l);

    @Test
    @DisplayName("GetUsersByQuery returns required list by query")
    void GetUsersByQuery_ReturnsRequiredListByQuery() {
        when(userRepository.findByQuery("Java")).thenReturn(users);
        var serviceUsers = userService.getUsersByQuery("Java");
        assertEquals(users.size(), serviceUsers.size());
    }

    @Test
    @DisplayName("UpdateUser returns updated user")
    void updateUser() {
        when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(Optional.ofNullable(user));
        when(userRepository.save(Mockito.any(User.class)))
                .thenReturn(user);
        var userFromService = userService.updateUser(new CustomUserDetails(user), updateUserDto);
        assertEquals(userFromService.getTelegramChatId(), updateUserDto.getTelegramChatId());
    }

    @Test
    @DisplayName("UpdateUser should throws exception on non exist username")
    void InvalidUsername(){
        when(userRepository.findByUsername("test"))
                .thenThrow(new ResourceNotFoundException("User with this username "));
        assertThrows(ResourceNotFoundException.class,
                () -> userService.updateUser(new CustomUserDetails(user), updateUserDto));
    }
}
