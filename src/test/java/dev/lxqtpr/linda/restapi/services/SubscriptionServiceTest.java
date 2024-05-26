package dev.lxqtpr.linda.restapi.services;

import dev.lxqtpr.linda.restapi.core.configs.AvailableQueriesConfig;
import dev.lxqtpr.linda.restapi.core.exceptions.QueryDoesNotSupportException;
import dev.lxqtpr.linda.restapi.core.exceptions.ResourceNotFoundException;
import dev.lxqtpr.linda.restapi.doman.subscriptions.SubscriptionEntity;
import dev.lxqtpr.linda.restapi.doman.subscriptions.SubscriptionRepository;
import dev.lxqtpr.linda.restapi.doman.subscriptions.SubscriptionsService;
import dev.lxqtpr.linda.restapi.doman.user.User;
import dev.lxqtpr.linda.restapi.doman.user.UserRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @Spy
    private static ModelMapper modelMapper = new ModelMapper();

    @Mock
    private UserRepository userRepository;

    @Mock
    private AvailableQueriesConfig availableQueriesConfig;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private SubscriptionsService subscriptionsService;


    private final User user = User
            .builder()
            .id(1L)
            .telegramChatId(123L)
            .username("test")
            .password("test")
            .build();
    private final SubscriptionEntity subscription = SubscriptionEntity
            .builder()
            .query("Java")
            .userId(1L)
            .build();
    @BeforeAll
    static void init(){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(PRIVATE);
    }

    @Test
    @DisplayName("Check if query does not available throws QueryDoesNotSupportException")
    void notAvailableQuery(){
        assertThrows(QueryDoesNotSupportException.class,
                () -> subscriptionsService.isQueryAvailable("test"));
    }
    @Test
    @DisplayName("Check if user does not exist throws ResourceNotFoundException")
    void userDoesNotExist() {
        when(userRepository.findById(1L))
                .thenThrow(new ResourceNotFoundException("User with this username "));
        when(availableQueriesConfig.getQueries())
                .thenReturn(List.of("Java", "Kotlin"));
        assertThrows(ResourceNotFoundException.class,
                () -> subscriptionsService.createNewSubscription(1L, "Java")
        );
    }

    @Test
    @DisplayName("Check creating new subscription with correct data")
    void creatingNewSubscription() {
        when(userRepository.findById(1L))
                .thenReturn(Optional.ofNullable(user));
        when(availableQueriesConfig.getQueries())
                .thenReturn(List.of("Java", "Kotlin"));
        subscriptionsService.createNewSubscription(1L, "Java");
        verify(subscriptionRepository, times(1))
                .save(Mockito.any(SubscriptionEntity.class));

    }
    @Test
    @DisplayName("Check removeSubscription with correct data")
    void removeSubscription() {
        when(subscriptionRepository.findSubscriptionByUserIdAndQuery(1L, "Java"))
                .thenReturn(Optional.ofNullable(subscription));
        when(availableQueriesConfig.getQueries())
                .thenReturn(List.of("Java", "Kotlin"));
        subscriptionsService.removeSubscription(1L, "Java");
        verify(subscriptionRepository, times(1))
                .delete(Mockito.any(SubscriptionEntity.class));
    }
}
