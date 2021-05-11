package danielszraramka.projects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SchoolSecretarysOfficeApplication {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "production");
        SpringApplication.run(SchoolSecretarysOfficeApplication.class, args);
    }

}
