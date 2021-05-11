package danielszraramka.projects.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SchoolDto {
    private Long schoolId;
    private String taxNumber;
    private String fullNameOfSchool;
    private String shortName;
    private String type;
    private String phoneNumber;
    private String mail;
    private AddressDto addressDto;
}
