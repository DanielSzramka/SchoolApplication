package danielszraramka.projects.service.command;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class CreateSchoolCommand {

    private final Long idCreateSchoolCommand;
    private final String taxNumber;
    private final String fullNameOfSchool;
    private final String shortName;
    private final String type;
    private final String phoneNumber;
    private final String mail;
    private final CreateAddressCommand address;
}
