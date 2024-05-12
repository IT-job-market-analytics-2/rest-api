package dev.lxqtpr.linda.restapi.doman.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginUserDto {
    @NotEmpty(message = "Username is mandatory")
    @Size(min = 3, message = "Username can not be shorter than 3 characters")
    @Size(max = 50, message = "Username can not be longer than 50 characters")
    private String username;

    @NotEmpty(message = "Password is mandatory")
    @Size(min = 6, message = "Password can not be shorter than 6 characters")
    @Size(max = 50, message = "Password can not be longer than 50 characters")
    private String password;
}
