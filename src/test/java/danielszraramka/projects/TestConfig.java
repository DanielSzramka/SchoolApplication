package danielszraramka.projects;

import danielszraramka.projects.config.authentication.UserWithId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;

@Configuration
@Profile("mockMvcTest")
public class TestConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            if (username.equals("parent")) {
                return new UserWithId(1L, "parent", "test", Arrays.asList(new SimpleGrantedAuthority("ROLE_PARENT")));
            } else {
                throw new UsernameNotFoundException("User not found");
            }
        };
    }
}
