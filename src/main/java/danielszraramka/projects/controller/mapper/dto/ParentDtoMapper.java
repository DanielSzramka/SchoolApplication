package danielszraramka.projects.controller.mapper.dto;

import danielszraramka.projects.controller.dto.AddressDto;
import danielszraramka.projects.controller.dto.ParentDto;
import danielszraramka.projects.model.Address;
import danielszraramka.projects.model.Parent;
import danielszraramka.projects.model.Person;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParentDtoMapper {

    public ParentDto mapFromParent(Parent parent) {

        final Person person = parent.getPerson();
        final Address address = person.getAddress();
        AddressDto addressDto = AddressDto.builder()
                .zipCode(address.getZipCode())
                .city(address.getCity())
                .street(address.getStreet())
                .flatNumber(address.getFlatNumber())
                .apartmentNumber(address.getApartmentNumber())
                .build();
        return ParentDto.builder()
                .id(parent.getParentId())
                .name(person.getName())
                .secondName(person.getSecondName())
                .surname(person.getSurname())
                .citizenship(person.getCitizenship())
                .identityNumber(person.getIdentityNumber())
                .nationality(person.getNationality())
                .address(addressDto)
                .phoneNumber(parent.getPhoneNumber())
                .build();

    }

    public List<ParentDto> mapFromParents(List<Parent> parents) {
        return parents.stream()
                .map(this::mapFromParent)
                .collect(Collectors.toList());
    }
}
