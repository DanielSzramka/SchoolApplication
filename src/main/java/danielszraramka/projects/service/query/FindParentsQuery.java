package danielszraramka.projects.service.query;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class FindParentsQuery {
    private final String name;
    private final String surname;
    private final String phoneNumber;
    private final String city;
    private final String street;
}
