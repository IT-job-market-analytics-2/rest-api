package dev.lxqtpr.linda.restapi.doman.subscriptions;

import dev.lxqtpr.linda.restapi.core.configs.AvailableQueriesConfig;
import dev.lxqtpr.linda.restapi.core.exceptions.QueryDoesNotSupportException;
import dev.lxqtpr.linda.restapi.core.exceptions.ResourceNotFoundException;
import dev.lxqtpr.linda.restapi.core.exceptions.ValueAlreadyExistException;
import dev.lxqtpr.linda.restapi.doman.subscriptions.dto.ResponseSubscriptionsDto;
import dev.lxqtpr.linda.restapi.doman.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class SubscriptionsService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final AvailableQueriesConfig availableQueriesConfig;
    private final ModelMapper modelMapper;

    public List<ResponseSubscriptionsDto> getUserSubscriptions(Long userId){
        return subscriptionRepository
                .findByUserId(userId)
                .stream()
                .map(el -> modelMapper.map(el, ResponseSubscriptionsDto.class))
                .toList();
    }
    @Transactional
    public List<ResponseSubscriptionsDto> createNewSubscription(Long userId, String query){
        isQueryAvailable(query);

        userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with this id does not exist"));

        var subscriptionToSave = SubscriptionEntity
                .builder()
                .query(query)
                .userId(userId)
                .build();
        try {
            subscriptionRepository.save(subscriptionToSave);
        }catch (DbActionExecutionException ex){
            throw new ValueAlreadyExistException("User already subscribed to this query");
        }
        return getUserSubscriptions(userId);
    }
    public List<ResponseSubscriptionsDto> removeSubscription(Long userId, String query) {
        isQueryAvailable(query);

        var subscription = subscriptionRepository
                .findSubscriptionByUserIdAndQuery(userId, query)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription query does not exist"));
        subscriptionRepository.delete(subscription);

        return getUserSubscriptions(userId);
    }
    public void isQueryAvailable(String query){
         getAvailableQueries()
                .stream()
                .filter(el -> el.getQuery().equalsIgnoreCase(query))
                .findFirst()
                .orElseThrow(() -> new QueryDoesNotSupportException("Non supported query"));
    }
    public List<ResponseSubscriptionsDto> getAvailableQueries(){
        return availableQueriesConfig
                .getQueries()
                .stream()
                .map(ResponseSubscriptionsDto::new)
                .toList();
    }
}
