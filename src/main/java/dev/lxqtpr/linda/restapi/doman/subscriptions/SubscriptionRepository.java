package dev.lxqtpr.linda.restapi.doman.subscriptions;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity, Long> {
    Optional<SubscriptionEntity> findByUserId(Long userId);
}
