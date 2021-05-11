package danielszraramka.projects.controller.mapper.dto;

import danielszraramka.projects.controller.dto.AddressDto;
import danielszraramka.projects.controller.dto.SchoolDto;
import danielszraramka.projects.model.Address;
import danielszraramka.projects.model.School;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SchoolDtoMapper {
    public SchoolDto mapFromSchool(School school) {
        Address address = school.getAddress();
        AddressDto addressDto = AddressDto.builder()
                .zipCode(address.getZipCode())
                .city(address.getCity())
                .street(address.getStreet())
                .flatNumber(address.getFlatNumber())
                .apartmentNumber(address.getApartmentNumber())
                .build();

        return SchoolDto.builder()
                .schoolId(school.getSchoolId())
                .fullNameOfSchool(school.getFullNameOfSchool())
                .shortName(school.getShortName())
                .taxNumber(school.getTaxNumber())
                .addressDto(addressDto)
                .phoneNumber(school.getPhoneNumber())
                .mail(school.getMail())
                .build();
    }

    public List<SchoolDto> mapFromSchools(List<School> schools) {
        return schools.stream()
                .map(this::mapFromSchool)
                .collect(Collectors.toList());
    }
}
