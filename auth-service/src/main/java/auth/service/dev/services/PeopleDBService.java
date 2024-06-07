package auth.service.dev.services;

import auth.service.dev.models.Person;
import auth.service.dev.repositories.PeopleRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PeopleDBService {

    private final PeopleRepository peopleRepository;

    public Optional<Person> getByUsername(String username) {
        return peopleRepository.findPersonByUsername(username);
    }

    public Optional<Person> getByEmail(String email) {
        return peopleRepository.findPersonByEmail(email);
    }

    @Transactional(readOnly = false)
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional(readOnly = false)
    public void setIsConfirmStatusByUsername(String username) {
        peopleRepository.setIsConfirmStatusByUsername(username);
    }

}
