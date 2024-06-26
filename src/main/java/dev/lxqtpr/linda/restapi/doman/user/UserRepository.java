package dev.lxqtpr.linda.restapi.doman.user;

import io.micrometer.core.annotation.Timed;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends ListCrudRepository<User, Long> {

    @Timed("findUserByUsername")
    Optional<User> findByUsername(String username);

    @Timed("findUsersByQuery")
    @Query("select * from users u inner join subscriptions s on u.id = s.userId where s.query = :query")
    List<User> findByQuery(@Param("query") String query);
}
