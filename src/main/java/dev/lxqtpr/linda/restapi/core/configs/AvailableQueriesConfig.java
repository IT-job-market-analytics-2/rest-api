package dev.lxqtpr.linda.restapi.core.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix = "available")
public class AvailableQueriesConfig {
    private List<String> queries;
}