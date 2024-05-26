package dev.lxqtpr.linda.restapi.doman.analitics;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AnalyticsRepository extends CrudRepository<AnalyticsEntity, Long> {
    List<AnalyticsEntity> findAnalyticsEntitiesByDate(LocalDate date);

    List<AnalyticsEntity> findAnalyticsEntitiesByQueryAndDateBetween(
            String query,
            LocalDate dateStart,
            LocalDate dateEnd,
            Sort sort
    );

    Optional<AnalyticsEntity> findFirstByOrderByDateDesc();
}
