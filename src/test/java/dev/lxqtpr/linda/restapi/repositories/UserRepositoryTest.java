package dev.lxqtpr.linda.restapi.repositories;

import dev.lxqtpr.linda.restapi.doman.subscriptions.SubscriptionEntity;
import dev.lxqtpr.linda.restapi.doman.subscriptions.SubscriptionRepository;
import dev.lxqtpr.linda.restapi.doman.user.User;
import dev.lxqtpr.linda.restapi.doman.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.stream.IntStream;

@DataJdbcTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Container
    public static MySQLContainer<?> mySql =
            new MySQLContainer<>("mysql:8.0");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySql::getJdbcUrl);
        registry.add("spring.datasource.username", mySql::getUsername);
        registry.add("spring.datasource.password", mySql::getPassword);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    private final List<User> users = List.of(
            new User(null,"test2","123", 123L),
            new User(null,"test3","123", 123L),
            new User(null,"test4","123", 123L)
    );

    private final List<SubscriptionEntity> subscriptions = List.of(
            new SubscriptionEntity(null, null, "Java"),
            new SubscriptionEntity(null, null, "Java"),
            new SubscriptionEntity(null, null, "Java")
    );
    @Test
    void UserRepository_FindByQuery_ReturnsCorrectList(){
        var usersFromDb = userRepository.saveAll(users);
        IntStream.range(0, 3)
                .forEach(i -> subscriptions.get(i).setUserId(usersFromDb.get(i).getId()));
        subscriptionRepository.saveAll(subscriptions);
        var res = userRepository.findByQuery("Java");
        Assertions.assertThat(res).isNotNull();
        Assertions.assertThat(res.size()).isEqualTo(3);
    }
}
