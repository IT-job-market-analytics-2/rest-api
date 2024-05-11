package dev.lxqtpr.linda.restapi.authentication;

import dev.lxqtpr.linda.restapi.doman.user.dto.CreateUserDto;
import dev.lxqtpr.linda.restapi.doman.user.dto.LoginUserDto;
import dev.lxqtpr.linda.restapi.doman.user.dto.ResponseUserDto;
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

    @PostMapping("/registration")
    public ResponseUserDto registration(@RequestBody @Valid CreateUserDto createUserDto){
        return authenticationService.registration(createUserDto);
    }

    @PostMapping("/login")
    public ResponseUserDto login(@RequestBody @Valid LoginUserDto loginUserDto){
        return authenticationService.login(loginUserDto);
    }
    @PostMapping("/refresh")
    public ResponseUserDto refreshTokens(HttpServletRequest request){
        return authenticationService.refreshTokens(request);
    }
}
