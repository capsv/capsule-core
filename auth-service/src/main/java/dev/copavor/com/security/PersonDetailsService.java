package dev.copavor.com.security;

import dev.copavor.com.models.Person;
import dev.copavor.com.repositories.PeopleRepository;
import dev.copavor.com.utils.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonDetailsService implements UserDetailsService {

    private final PeopleRepository peopleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person=peopleRepository.findPersonByUsername(username)
                .orElseThrow(()-> new NotFoundException("Person with username: "+username+" not found"));

        return new PersonDetails(person);
    }
}
