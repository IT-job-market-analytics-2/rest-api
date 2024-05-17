package dev.lxqtpr.linda.restapi.doman.subscriptions;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity, Long> {
    List<SubscriptionEntity> findByUserId(Long userId);
    Optional<SubscriptionEntity> findSubscriptionByUserIdAndQuery(Long userId, String query);
}
