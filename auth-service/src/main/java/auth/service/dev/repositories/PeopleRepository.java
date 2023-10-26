package auth.service.dev.repositories;

import auth.service.dev.models.Person;
import auth.service.dev.repositories.common.CommonRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends CommonRepository<Person> {

    Optional<Person> findPersonByUsername(String username);

    Optional<Person> findPersonByEmail(String email);
}