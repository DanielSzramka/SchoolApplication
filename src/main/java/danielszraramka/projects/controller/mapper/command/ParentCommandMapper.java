package danielszraramka.projects.controller.mapper.command;

import danielszraramka.projects.controller.request.CreateParentRequestAndUpdate;
import danielszraramka.projects.service.command.CreateAddressCommand;
import danielszraramka.projects.service.command.CreateParentCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParentCommandMapper {

    @Qualifier("controllerAddressMapper")
    private final AddressCommandMapper addressMapper;


    public CreateParentCommand mapFromCreateParentRequest(CreateParentRequestAndUpdate createParentRequest) {
        return mapFromCreateParentRequest(createParentRequest, null);
    }

    public CreateParentCommand mapFromCreateParentRequest(CreateParentRequestAndUpdate createParentRequest, Long parentId) {

        CreateAddressCommand address = addressMapper.mapFromCreateAddressRequest(createParentRequest.getAddress());

        return CreateParentCommand.builder()
                .idCreateParentCommand(parentId)
                .mail(createParentRequest.getMail())
                .password(createParentRequest.getPassword())
                .name(createParentRequest.getName())
                .secondName(createParentRequest.getSecondName())
                .surname(createParentRequest.getSurname())
                .identityNumber(createParentRequest.getIdentityNumber())
                .nationality(createParentRequest.getNationality())
                .citizenship(createParentRequest.getCitizenship())
                .address(address)
                .phoneNumber(createParentRequest.getPhoneNumber())
                .build();
    }


}
