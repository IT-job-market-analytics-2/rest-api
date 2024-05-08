package dev.lxqtpr.linda.restapi.doman.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserDto {
    @NotEmpty(message = "Username name is mandatory")
    private String username;

    @NotNull(message = "TelegramChatId name is mandatory")
    private Long telegramChatId;
}
