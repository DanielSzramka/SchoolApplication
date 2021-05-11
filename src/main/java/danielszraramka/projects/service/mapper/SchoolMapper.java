package danielszraramka.projects.service.mapper;

import danielszraramka.projects.model.Address;
import danielszraramka.projects.model.School;
import danielszraramka.projects.service.command.CreateSchoolCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchoolMapper {

    @Qualifier("serviceAddressMapper")
    private final AddressMapper addressMapper;

    public School mapFromCreateSchoolCommand(CreateSchoolCommand createSchoolCommand) {
        Address address = addressMapper.mapFromCreateAddressCommand(createSchoolCommand.getAddress());
        return School.builder()
                .taxNumber(createSchoolCommand.getTaxNumber())
                .fullNameOfSchool(createSchoolCommand.getFullNameOfSchool())
                .shortName(createSchoolCommand.getShortName())
                .type(createSchoolCommand.getType())
                .phoneNumber(createSchoolCommand.getPhoneNumber())
                .mail(createSchoolCommand.getMail())
                .address(address)
                .build();
    }

    public void updateSchool(CreateSchoolCommand createSchoolCommand, School school) {

        school.setTaxNumber(createSchoolCommand.getTaxNumber());
        school.setFullNameOfSchool(createSchoolCommand.getFullNameOfSchool());
        school.setShortName(createSchoolCommand.getShortName());
        school.setMail(createSchoolCommand.getMail());
        school.setPhoneNumber(createSchoolCommand.getPhoneNumber());
        Address addressToUpdate = school.getAddress();
        addressToUpdate.setZipCode(createSchoolCommand.getAddress().getZipCode());
        addressToUpdate.setCity(createSchoolCommand.getAddress().getCity());
        addressToUpdate.setStreet(createSchoolCommand.getAddress().getStreet());
        addressToUpdate.setApartmentNumber(createSchoolCommand.getAddress().getApartmentNumber());
        addressToUpdate.setFlatNumber(createSchoolCommand.getAddress().getFlatNumber());
        school.setAddress(addressToUpdate);
    }
}
