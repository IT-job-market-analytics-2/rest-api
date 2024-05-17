package dev.lxqtpr.linda.restapi.doman.analitics;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
@Table(name = "vacancy_analytics")
public class VacancyAnalyticsEntity {

    @Id
    private Long id;

    @Column("date")
    private LocalDate date;

    @Column("query")
    private String query;

    @Column("vacancy_count")
    private Long vacancyCount;

    @Column("average_salary")
    private Double salary;
}
