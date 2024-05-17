package dev.lxqtpr.linda.restapi.doman.subscriptions.dto;

import lombok.Data;

@Data
public class CreateSubscriptionDto {
    private Long userId;
    private String query;
}
