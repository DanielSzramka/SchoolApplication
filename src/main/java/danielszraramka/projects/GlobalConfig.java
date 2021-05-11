package danielszraramka.projects;

import danielszraramka.projects.config.MessageSourceConfig;
import danielszraramka.projects.config.WebSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@Import({WebSecurityConfig.class, MessageSourceConfig.class})
@EnableSwagger2
public class GlobalConfig {


}
