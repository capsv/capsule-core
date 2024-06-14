package org.capsule.com.repositories.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.capsule.com.models.common.CEntity;

@NoRepositoryBean
public interface CustomRepository<E extends CEntity> extends JpaRepository<E, Long> {

}
