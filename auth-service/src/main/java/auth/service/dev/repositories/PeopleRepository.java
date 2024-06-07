package auth.service.dev.repositories;

import auth.service.dev.models.Person;
import auth.service.dev.repositories.common.CommonRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends CommonRepository<Person> {

    Optional<Person> findPersonByUsername(String username);

    Optional<Person> findPersonByEmail(String email);

    @Modifying
    @Query(value = """
        UPDATE auth.person SET is_confirm = true WHERE username = :username
        """, nativeQuery = true)
    void setIsConfirmStatusByUsername(@Param("username") String username);
}

