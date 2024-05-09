package dev.lxqtpr.linda.restapi.doman.user;

import dev.lxqtpr.linda.restapi.doman.user.dto.CreateUserDto;
import dev.lxqtpr.linda.restapi.doman.user.dto.ResponseUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseUserDto createUser(@RequestBody CreateUserDto createUserDto){
        return userService.createUser(createUserDto);
    }

    @GetMapping()
    public List<ResponseUserDto> getUserByQuery(@RequestParam("subscribedTo") String subscribedTo){
        return userService.getUserByQuery(subscribedTo);
    }
}
