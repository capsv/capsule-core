package org.capsule.com.tasks.repositories;

import org.capsule.com.tasks.models.CEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ICEntityJpaRepository<E extends CEntity> extends JpaRepository<E, Long> {

}
