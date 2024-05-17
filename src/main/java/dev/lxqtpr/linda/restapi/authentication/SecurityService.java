package dev.lxqtpr.linda.restapi.authentication;

import dev.lxqtpr.linda.restapi.core.exceptions.ResourceNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    public CustomUserDetails convertAuthentication(Authentication currentUser){
        if (currentUser instanceof UsernamePasswordAuthenticationToken authenticationToken) {
            return (CustomUserDetails) authenticationToken.getPrincipal();
        } else {
            throw new ResourceNotFoundException("Unknown principal type");
        }
    }
}
