package dev.copavor.com.services;


import dev.copavor.com.common.Role;
import dev.copavor.com.dtos.requests.PersonRegisterReqst;
import dev.copavor.com.models.Person;
import dev.copavor.com.repositories.PeopleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PeopleDBService {

    private final PeopleRepository peopleRepository;

    public Optional<Person> getByUsername(String username){
        return peopleRepository.findPersonByUsername(username);
    }

    public Optional<Person> getByEmail(String email){
        return peopleRepository.findPersonByEmail(email);
    }

    @Transactional(readOnly = false)
    public void save(Person person){
        peopleRepository.save(person);
    }
}
