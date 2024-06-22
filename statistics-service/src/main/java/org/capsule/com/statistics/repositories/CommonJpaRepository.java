package org.capsule.com.statistics.repositories;

import org.capsule.com.statistics.models.CEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CommonJpaRepository<E extends CEntity> extends JpaRepository<E, Long> {

}
