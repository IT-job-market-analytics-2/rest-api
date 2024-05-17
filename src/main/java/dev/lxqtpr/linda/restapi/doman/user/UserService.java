package dev.lxqtpr.linda.restapi.doman.user;

import dev.lxqtpr.linda.restapi.authentication.CustomUserDetails;
import dev.lxqtpr.linda.restapi.core.exceptions.ResourceNotFoundException;
import dev.lxqtpr.linda.restapi.doman.user.dto.EditUserDto;
import dev.lxqtpr.linda.restapi.doman.user.dto.GetUserDto;
import dev.lxqtpr.linda.restapi.doman.user.dto.ResponseUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public List<ResponseUserDto> getUsersByQuery(String subscribedTo) {
        var users = userRepository.findByQuery(subscribedTo);
        return users
                .stream()
                .map( el -> modelMapper.map(el, ResponseUserDto.class))
                .toList();
    }
    public GetUserDto getUserDto(CustomUserDetails currentUser){
        return new GetUserDto(
                currentUser.getUsername(),
                currentUser.getTelegramChatId()
        );
    }

    public GetUserDto updateUser(CustomUserDetails currentUser, EditUserDto editUserDto) {
        var userDetails = getUserDto(currentUser);
        var userFromDb = userRepository
                .findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User with this username does not exist"));
        userFromDb.setTelegramChatId(editUserDto.getTelegramChatId());
        return modelMapper.map(userRepository.save(userFromDb), GetUserDto.class);
    }
}
