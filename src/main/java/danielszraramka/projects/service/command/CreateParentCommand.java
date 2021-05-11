package danielszraramka.projects.service.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CreateParentCommand {

    private Long idCreateParentCommand;
    private String mail;
    private String password;
    private String name;
    private String secondName;
    private String surname;
    private String identityNumber;
    private String citizenship;
    private String nationality;
    private CreateAddressCommand address;
    private String phoneNumber;
}
