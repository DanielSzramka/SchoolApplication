package danielszraramka.projects.service;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.boot.test.web.client.TestRestTemplate.HttpClientOption.ENABLE_COOKIES;

@ActiveProfiles("integrationTest")
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class IntegrationTest {

    protected TestRestTemplate restTemplate;
    protected String baseUrl;
    @LocalServerPort
    private int port;
    @Autowired
    private Flyway flyway;
    private String username;
    private String password;

    protected IntegrationTest() {
    }

    protected IntegrationTest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @DynamicPropertySource
    static void registerIntegrationTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "${INTEGRATION_DATASOURCE_URL}");
        registry.add("spring.datasource.username", () -> "${INTEGRATION_DATASOURCE_USERNAME}");
        registry.add("spring.datasource.password", () -> "${INTEGRATION_DATASOURCE_PASSWORD}");
    }

    @BeforeAll
    public void beforeAll() {
        flyway.clean();
        flyway.migrate();

        baseUrl = "http://localhost:" + port;

        if (username != null && password != null) {
            restTemplate = new TestRestTemplate(username, password, ENABLE_COOKIES);
            restTemplate.postForEntity(baseUrl + "/user/login", null, String.class);
        } else {
            restTemplate = new TestRestTemplate();
        }
    }
}
