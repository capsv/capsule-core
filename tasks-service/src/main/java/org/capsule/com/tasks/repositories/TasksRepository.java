package org.capsule.com.tasks.repositories;

import org.capsule.com.tasks.models.Task;
import org.springframework.stereotype.Repository;

@Repository
public interface TasksRepository extends ICEntityJpaRepository<Task> {

}
