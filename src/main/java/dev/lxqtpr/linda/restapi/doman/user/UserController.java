package dev.lxqtpr.linda.restapi.doman.user;

import dev.lxqtpr.linda.restapi.authentication.SecurityService;
import dev.lxqtpr.linda.restapi.doman.user.dto.EditUserDto;
import dev.lxqtpr.linda.restapi.doman.user.dto.GetUserDto;
import dev.lxqtpr.linda.restapi.doman.user.dto.ResponseUserDto;
import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SecurityService securityService;

    @Timed("getUsersByQueryController")
    @GetMapping
    public List<ResponseUserDto> getUsersByQuery(@RequestParam("query") String subscribedTo){
        return userService.getUsersByQuery(subscribedTo);
    }
    @Timed("getUserInfoController")
    @GetMapping("/getUserInfo")
    public GetUserDto getUserInfo(Authentication currentUser){
        var user = securityService.convertAuthentication(currentUser);
        return userService.getUserDto(user);
    }
    @Timed("editUserInfoController")
    @PutMapping
    public GetUserDto editUserInfo(Authentication currentUser, @Valid @RequestBody EditUserDto editUserDto){
        var user = securityService.convertAuthentication(currentUser);
        return userService.updateUser(user, editUserDto);
    }
}
