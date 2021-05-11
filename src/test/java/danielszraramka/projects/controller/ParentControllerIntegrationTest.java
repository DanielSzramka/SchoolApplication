package danielszraramka.projects.controller;

import danielszraramka.projects.controller.request.CreateParentRequestAndUpdate;
import danielszraramka.projects.service.IntegrationTest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;

public class ParentControllerIntegrationTest extends IntegrationTest {

    public ParentControllerIntegrationTest() {
        super("parent", "testtest");
    }

    @Order(1)
    @Test
    public void shouldCreateParent() {

        CreateParentRequestAndUpdate createParentRequestAndUpdate = CreateParentRequestAndUpdate.builder()
                .mail("dfisijisdf@ijiads.com")
                .password("testtest")
                .name("asdasdsa")
                .secondName("gasasdfa")
                .surname("asdasd")
                .phoneNumber("000111222")
                .build();

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/parents", createParentRequestAndUpdate, String.class);

        System.out.println(response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
    }
}
