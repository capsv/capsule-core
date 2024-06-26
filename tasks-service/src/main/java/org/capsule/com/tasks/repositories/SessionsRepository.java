package org.capsule.com.tasks.repositories;

import java.util.List;
import org.capsule.com.tasks.models.Session;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionsRepository extends ICEntityJpaRepository<Session> {

    @Query(value = """
        SELECT *  FROM task.session WHERE  username = :username
        """, nativeQuery = true)
    List<Session> findAllByUsername(@Param("username") String username);

    Session findByUsernameAndTaskId(String username, Long taskId);
}
