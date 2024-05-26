package dev.lxqtpr.linda.restapi.doman.analitics.dto;

import lombok.Data;

@Data
public class ResponseAnalyticsDto {
    private String query;
    private Long vacancyCount;
    private Double averageSalary;
}
