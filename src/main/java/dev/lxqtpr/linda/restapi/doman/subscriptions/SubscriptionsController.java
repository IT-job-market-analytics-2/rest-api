package dev.lxqtpr.linda.restapi.doman.subscriptions;

import dev.lxqtpr.linda.restapi.authentication.SecurityService;
import dev.lxqtpr.linda.restapi.doman.subscriptions.dto.ResponseSubscriptionsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subscriptions")
public class SubscriptionsController {
    private final SubscriptionsService subscriptionsService;
    private final SecurityService securityService;

    @GetMapping
    public List<ResponseSubscriptionsDto> getUserSubscriptions(Authentication authentication){
        var currentUser = securityService.convertAuthentication(authentication);
        return subscriptionsService.getUserSubscriptions(currentUser.getUserId());
    }
    @GetMapping("/allAvailable")
    public List<ResponseSubscriptionsDto> getAllAvailableSubscriptions() {
        return subscriptionsService.getAvailableQueries();
    }

    @PostMapping("/{query}")
    public List<ResponseSubscriptionsDto> addSubscription(Authentication authentication, @PathVariable String query) {
        var currentUser = securityService.convertAuthentication(authentication);
        return subscriptionsService.createNewSubscription(currentUser.getUserId(), query);
    }

    @DeleteMapping("/{query}")
    public List<ResponseSubscriptionsDto> removeSubscription(Authentication authentication, @PathVariable String query) {
        var currentUser = securityService.convertAuthentication(authentication);
        return subscriptionsService.removeSubscription(currentUser.getUserId(), query);
    }
}
