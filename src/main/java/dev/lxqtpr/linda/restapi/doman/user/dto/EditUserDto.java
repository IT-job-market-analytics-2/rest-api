package dev.lxqtpr.linda.restapi.doman.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditUserDto {
    @NotNull
    private Long telegramChatId;
}
