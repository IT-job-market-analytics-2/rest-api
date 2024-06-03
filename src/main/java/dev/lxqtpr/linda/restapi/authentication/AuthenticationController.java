package dev.lxqtpr.linda.restapi.authentication;

import dev.lxqtpr.linda.restapi.doman.user.dto.CreateUserDto;
import dev.lxqtpr.linda.restapi.doman.user.dto.LoginUserDto;
import dev.lxqtpr.linda.restapi.doman.user.dto.ResponseUserDto;
import io.micrometer.core.annotation.Timed;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Timed
    @PostMapping("/registration")
    public ResponseUserDto registration(@RequestBody @Valid CreateUserDto createUserDto){
        return authenticationService.registration(createUserDto);
    }

    @Timed
    @PostMapping("/login")
    public ResponseUserDto login(@RequestBody @Valid LoginUserDto loginUserDto){
        return authenticationService.login(loginUserDto);
    }

    @Timed
    @PostMapping("/refresh")
    public ResponseUserDto refreshTokens(HttpServletRequest request){
        return authenticationService.refreshTokens(request);
    }
}
