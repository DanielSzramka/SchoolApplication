package danielszraramka.projects.service.mapper;

import danielszraramka.projects.model.Role;
import danielszraramka.projects.model.RoleType;
import danielszraramka.projects.model.User;
import danielszraramka.projects.service.command.CreateParentCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public User mapFromCreateParentCommand(CreateParentCommand command) {

        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder()
                .roleType(RoleType.PARENT)
                .build());

        User user = User.builder()
                .mail(command.getMail())
                .passwordHash(passwordEncoder.encode(command.getPassword()))
                .roles(roles)
                .build();

        for (Role role : user.getRoles()) {
            role.setUser(user);
        }
        return user;
    }


}
