package danielszraramka.projects.service.command;

import danielszraramka.projects.model.School;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class CreateHeadmasterCommand {

    private Long idCreateHeadmasterCommand;
    private String mail;
    private String password;
    private String name;
    private String secondName;
    private String surname;
    private String identityNumber;
    private String citizenship;
    private String nationality;
    private CreateAddressCommand address;
    private School school;
    private Date startDate;
}
