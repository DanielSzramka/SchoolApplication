package danielszraramka.projects.service.query;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class FindSchoolsQuery {
    private final String taxNumber;
    private final String fullNameOfSchool;
    private final String shortName;
    private final String type;
    private final String city;
    private final String street;
}
