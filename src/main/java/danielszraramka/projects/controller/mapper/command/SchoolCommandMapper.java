package danielszraramka.projects.controller.mapper.command;

import danielszraramka.projects.controller.request.CreateSchoolRequestAndUpdate;
import danielszraramka.projects.service.command.CreateAddressCommand;
import danielszraramka.projects.service.command.CreateSchoolCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchoolCommandMapper {
    @Qualifier("controllerAddressMapper")
    private final AddressCommandMapper addressMapper;

    public CreateSchoolCommand mapFromCreateSchoolRequest(CreateSchoolRequestAndUpdate createSchoolRequest) {
        return mapFromCreateSchoolRequest(createSchoolRequest, null);
    }


    public CreateSchoolCommand mapFromCreateSchoolRequest(CreateSchoolRequestAndUpdate createSchoolRequest, Long schoolId) {
        CreateAddressCommand address = addressMapper.mapFromCreateAddressRequest(createSchoolRequest.getAddressRequest());
        return CreateSchoolCommand.builder()
                .idCreateSchoolCommand(schoolId)
                .taxNumber(createSchoolRequest.getTaxNumber())
                .fullNameOfSchool(createSchoolRequest.getFullNameOfSchool())
                .shortName(createSchoolRequest.getFullNameOfSchool())
                .type(createSchoolRequest.getType())
                .phoneNumber(createSchoolRequest.getPhoneNumber())
                .mail(createSchoolRequest.getMail())
                .address(address)
                .build();
    }
}
