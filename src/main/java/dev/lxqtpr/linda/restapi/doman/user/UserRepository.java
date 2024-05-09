package dev.lxqtpr.linda.restapi.doman.user;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    @Query("select * from users u inner join subscriptions s on u.id = s.userId where s.query = :query")
    List<UserEntity> findByQuery(@Param("query") String query);
}
