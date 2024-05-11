package dev.lxqtpr.linda.restapi.doman.user.dto;

import lombok.Data;

@Data
public class ResponseUserDto{
    private Long id;
    private String username;
    private Long telegramChatId;
    private String accessToken;
    private String refreshToken;
}