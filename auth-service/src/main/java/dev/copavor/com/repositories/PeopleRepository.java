package dev.copavor.com.repositories;

import dev.copavor.com.models.Person;
import dev.copavor.com.repositories.common.CommonRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends CommonRepository<Person> {

    Optional<Person> findPersonByUsername(String username);

    Optional<Person> findPersonByEmail(String email);
}