package pcf.crskdev.jersey.demo.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pcf.crskdev.jersey.demo.common.repositories.StudentRepository;

@Service
public final class StudentUsersDetailsService implements UserDetailsService {

    private final StudentRepository studentRepository;

    public StudentUsersDetailsService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {
        return this.studentRepository
            .findOne(username)
            .map(s -> User
                .withUsername(s.getUsername())
                .password(s.getPasswordHash())
                .roles("STUDENT")
                .build()
            )
            .orElseThrow(() -> new UsernameNotFoundException("Username '" + username + "' not found"));
    }
}
