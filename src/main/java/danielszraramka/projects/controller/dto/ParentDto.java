package danielszraramka.projects.controller.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class ParentDto {

    private Long id;
    private String name;
    private String secondName;
    private String surname;
    private String identityNumber;
    private String citizenship;
    private String nationality;
    private AddressDto address;
    private String phoneNumber;
}
