package dev.lxqtpr.linda.restapi.doman.subscriptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "subscriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionEntity {
    @Id
    private Long id;

    @Column(value = "userId")
    private Long userId;

    @Column(value = "query")
    private String query;
}
