package danielszraramka.projects.service;

import danielszraramka.projects.config.authentication.UserWithId;
import danielszraramka.projects.model.Role;
import danielszraramka.projects.model.User;
import danielszraramka.projects.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Qualifier("defaultUserDetailsService")
@RequiredArgsConstructor
public class DefaultUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        final User user = userRepository.findUserByMail(mail)
                .orElseThrow(() -> new UsernameNotFoundException("User with " + mail + " not exists"));
        return new UserWithId(user.getId(), user.getMail(), user.getPasswordHash(), mapRolesToGrantedAuthorities(user));
    }

    private Collection<GrantedAuthority> mapRolesToGrantedAuthorities(User user) {
        return user.getRoles().stream()
                .map(Role::getRoleType)
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
