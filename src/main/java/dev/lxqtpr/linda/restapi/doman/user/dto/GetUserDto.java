package dev.lxqtpr.linda.restapi.doman.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDto {
    String username;
    Long telegramChatId;
}