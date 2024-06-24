package org.capsule.com.tasks.repositories;

import java.util.List;
import org.capsule.com.tasks.models.TaskManage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TasksManageRepository extends ICEntityJpaRepository<TaskManage> {

    @Query(value = """
        SELECT *  FROM task.tasks_manage WHERE  username = :username
        """, nativeQuery = true)
    List<TaskManage> findAllByUsername(@Param("username") String username);
}
