package dev.lxqtpr.linda.restapi.doman.user.dto;

import lombok.Data;
import lombok.Value;

@Data
public class ResponseUserDto{
    private Long id;
    private String username;
    private Long telegramChatId;
}