package dev.lxqtpr.linda.restapi.doman.analitics;

import dev.lxqtpr.linda.restapi.doman.analitics.dto.ResponseAnalyticsDto;
import dev.lxqtpr.linda.restapi.doman.analitics.dto.ResponseAnalyticsHistoryDto;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @Timed("getAnalyticsByQueryController")
    @GetMapping("/byQuery")
    public List<ResponseAnalyticsDto> byQuery(){
        return analyticsService.byQuery();
    }

    @Timed("getHistoryQueryController")
    @GetMapping("/history/{query}")
    public List<ResponseAnalyticsHistoryDto> historyQuery(@PathVariable String query, @Param("depth") int depth){
        return analyticsService.historyQuery(query, depth);
    }
}
