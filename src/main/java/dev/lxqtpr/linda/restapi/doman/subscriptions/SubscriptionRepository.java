package dev.lxqtpr.linda.restapi.doman.subscriptions;

import io.micrometer.core.annotation.Timed;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity, Long> {
    @Timed("findSubscriptionByUserId")
    List<SubscriptionEntity> findByUserId(Long userId);
    @Timed("findSubscriptionByUserIdAndQuery")
    Optional<SubscriptionEntity> findSubscriptionByUserIdAndQuery(Long userId, String query);
}
