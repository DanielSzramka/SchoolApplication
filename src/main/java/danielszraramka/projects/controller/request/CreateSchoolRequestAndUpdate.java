package danielszraramka.projects.controller.request;

import danielszraramka.projects.controller.validation.CreateGroupValidation;
import danielszraramka.projects.controller.validation.TaxNumberNotExist;
import danielszraramka.projects.controller.validation.UpdateGroupValidation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class CreateSchoolRequestAndUpdate {
    private Long idCreateSchoolRequest;
    @NotBlank(groups = {CreateGroupValidation.class, UpdateGroupValidation.class})
    @TaxNumberNotExist(groups = {CreateGroupValidation.class})
    private String taxNumber;
    private String FullNameOfSchool;
    private String shortName;
    private String type;
    private String phoneNumber;
    private String mail;
    private AddressRequest addressRequest;
}
