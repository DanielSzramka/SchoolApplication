package danielszraramka.projects.controller.request;

import danielszraramka.projects.controller.validation.CreateGroupValidation;
import danielszraramka.projects.controller.validation.EmailNotExist;
import danielszraramka.projects.controller.validation.PhoneNumber;
import danielszraramka.projects.controller.validation.UpdateGroupValidation;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class CreateParentRequestAndUpdate {

    private Long idCreateParentRequestAndUpdate;

    @NotBlank(groups = {CreateGroupValidation.class, UpdateGroupValidation.class})
    @Email(groups = {CreateGroupValidation.class, UpdateGroupValidation.class})
    @EmailNotExist(groups = {CreateGroupValidation.class})
    private String mail;

    @NotBlank
    @Size(min = 8, groups = {CreateGroupValidation.class, UpdateGroupValidation.class})
    private String password;

    @NotBlank(groups = {CreateGroupValidation.class, UpdateGroupValidation.class})
    @Size(min = 2, groups = {CreateGroupValidation.class, UpdateGroupValidation.class})
    private String name;

    private String secondName;

    @NotBlank(groups = {CreateGroupValidation.class, UpdateGroupValidation.class})
    @Size(min = 2, groups = {CreateGroupValidation.class, UpdateGroupValidation.class})
    private String surname;

    private String identityNumber;

    private String citizenship;

    private String nationality;

    private AddressRequest address;
    @PhoneNumber(groups = {CreateGroupValidation.class, UpdateGroupValidation.class})
    private String phoneNumber;
}
