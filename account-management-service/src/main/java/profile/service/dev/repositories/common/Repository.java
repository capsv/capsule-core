package profile.service.dev.repositories.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import profile.service.dev.models.common.Entity;

@NoRepositoryBean
public interface Repository<E extends Entity> extends JpaRepository<E, Long> {

}
