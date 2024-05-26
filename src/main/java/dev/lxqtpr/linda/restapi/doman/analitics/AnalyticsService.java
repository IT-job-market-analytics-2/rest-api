package dev.lxqtpr.linda.restapi.doman.analitics;

import dev.lxqtpr.linda.restapi.doman.analitics.dto.ResponseAnalyticsDto;
import dev.lxqtpr.linda.restapi.doman.analitics.dto.ResponseAnalyticsHistoryDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final AnalyticsRepository analyticsRepository;
    private final ModelMapper modelMapper;

    public List<ResponseAnalyticsDto> byQuery() {
        Optional<LocalDate> maxDate = getMaxDate();

        if (maxDate.isEmpty()) {
            return Collections.emptyList();
        }

        return analyticsRepository.findAnalyticsEntitiesByDate(maxDate.get())
                .stream()
                .map((element) -> modelMapper.map(element, ResponseAnalyticsDto.class))
                .toList();

    }

    private Optional<LocalDate> getMaxDate() {
        return analyticsRepository
                .findFirstByOrderByDateDesc()
                .map(AnalyticsEntity::getDate);
    }

    public List<ResponseAnalyticsHistoryDto> historyQuery(String query, int depth) {
        LocalDate endDate = getMaxDate().orElse(LocalDate.now());
        LocalDate startDate = endDate.minusDays(depth);

        return analyticsRepository.findAnalyticsEntitiesByQueryAndDateBetween(
                query,
                startDate,
                endDate,
                Sort.by("date").ascending()
        )
                .stream()
                .map(el -> modelMapper.map(el, ResponseAnalyticsHistoryDto.class))
                .toList();
    }
}