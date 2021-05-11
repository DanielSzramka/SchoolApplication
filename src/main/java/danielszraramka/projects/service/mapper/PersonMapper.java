package danielszraramka.projects.service.mapper;

import danielszraramka.projects.model.Address;
import danielszraramka.projects.model.Person;
import danielszraramka.projects.service.command.CreateHeadmasterCommand;
import danielszraramka.projects.service.command.CreateParentCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonMapper {
    @Qualifier("serviceAddressMapper")
    private final AddressMapper addressMapper;


    public Person mapFromCreatePersonCommand(CreateParentCommand command) {
        Address address = addressMapper.mapFromCreateAddressCommand(command.getAddress());
        return Person.builder()
                .name(command.getName())
                .secondName(command.getSecondName())
                .surname(command.getSurname())
                .identityNumber(command.getIdentityNumber())
                .citizenship(command.getCitizenship())
                .nationality(command.getNationality())
                .address(address)
                .build();

    }

    public Person mapFromCreateHeadmasterCommand(CreateHeadmasterCommand headmasterCommand) {
        Address address = addressMapper.mapFromCreateAddressCommand(headmasterCommand.getAddress());

        return Person.builder()
                .name(headmasterCommand.getName())
                .secondName(headmasterCommand.getSecondName())
                .surname(headmasterCommand.getSurname())
                .identityNumber(headmasterCommand.getIdentityNumber())
                .citizenship(headmasterCommand.getCitizenship())
                .nationality(headmasterCommand.getNationality())
                .address(address)
                .build();
    }

}

