package dev.lxqtpr.linda.restapi.doman.analitics.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseAnalyticsHistoryDto {
    private Date date;
    private Long vacancyCount;
    private Double averageSalary;
}
