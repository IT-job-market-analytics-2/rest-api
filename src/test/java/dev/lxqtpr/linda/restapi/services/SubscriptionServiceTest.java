package dev.lxqtpr.linda.restapi.services;

import dev.lxqtpr.linda.restapi.authentication.CustomUserDetails;
import dev.lxqtpr.linda.restapi.core.configs.AvailableQueriesConfig;
import dev.lxqtpr.linda.restapi.core.exceptions.QueryDoesNotSupportException;
import dev.lxqtpr.linda.restapi.core.exceptions.ResourceNotFoundException;
import dev.lxqtpr.linda.restapi.doman.subscriptions.SubscriptionRepository;
import dev.lxqtpr.linda.restapi.doman.subscriptions.SubscriptionsService;
import dev.lxqtpr.linda.restapi.doman.user.UserEntity;
import dev.lxqtpr.linda.restapi.doman.user.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@ExtendWith(MockitoExtension.class)
@EnableConfigurationProperties
@SpringBootTest(classes = AvailableQueriesConfig.class)
class SubscriptionServiceTest {

    @Spy
    static ModelMapper modelMapper = new ModelMapper();

    @Mock
    private UserRepository userRepository;

    @SpyBean
    private AvailableQueriesConfig availableQueriesConfig;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private SubscriptionsService subscriptionsService;

    private static UserEntity user = UserEntity
            .builder()
            .id(1l)
            .telegramChatId(123l)
            .username("test")
            .password("test")
            .build();

    @BeforeAll
    static void init(){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(PRIVATE);
    }

    @Test
    @DisplayName("Check if query does not available")
    void notAvailableQuery(){
        System.out.println(availableQueriesConfig.getQueries());
        assertThrows(QueryDoesNotSupportException.class,
                () -> subscriptionsService.isQueryAvailable("test"));
    }
    @Test
    @DisplayName("Check if user does not exist")
    void userDoesNotExist(){
        when(userRepository.findById(1l))
                .thenThrow(new ResourceNotFoundException("User with this username "));
        assertThrows(ResourceNotFoundException.class,
                () -> subscriptionsService.createNewSubscription(1l, "Java")
        );
    }
}
