package dev.lxqtpr.linda.restapi.doman.analitics;

import io.micrometer.core.annotation.Timed;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AnalyticsRepository extends CrudRepository<AnalyticsEntity, Long> {

    @Timed("findAnalyticsEntitiesByDate")
    List<AnalyticsEntity> findAnalyticsEntitiesByDate(LocalDate date);

    @Timed("findAnalyticsEntitiesByQueryAndDateBetween")
    List<AnalyticsEntity> findAnalyticsEntitiesByQueryAndDateBetween(
            String query,
            LocalDate dateStart,
            LocalDate dateEnd,
            Sort sort
    );

    @Timed("findFirstByOrderByDateDesc")
    Optional<AnalyticsEntity> findFirstByOrderByDateDesc();
}
