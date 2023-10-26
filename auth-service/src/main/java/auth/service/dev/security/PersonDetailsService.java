package auth.service.dev.security;

import auth.service.dev.repositories.PeopleRepository;
import auth.service.dev.utils.exceptions.NotFoundException;
import auth.service.dev.models.Person;
import lombok.RequiredArgsConstructor;
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
