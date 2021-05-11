package danielszraramka.projects.service.mapper;

import danielszraramka.projects.model.Address;
import danielszraramka.projects.model.Parent;
import danielszraramka.projects.model.Person;
import danielszraramka.projects.model.User;
import danielszraramka.projects.service.command.CreateParentCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParentMapper {

    private final PersonMapper personMapper;

    public Parent mapFromCreateParentCommandAndUser(CreateParentCommand command, User user) {

        return Parent.builder()
                .phoneNumber(command.getPhoneNumber())
                .person(personMapper.mapFromCreatePersonCommand(command))
                .user(user)
                .build();
    }

    public void updateParent(CreateParentCommand command, Parent parent) {
        Person personToUpdate = parent.getPerson();
        personToUpdate.setName(command.getName());
        personToUpdate.setSecondName(command.getSecondName());
        personToUpdate.setSurname(command.getSurname());
        Address address = personToUpdate.getAddress();
        address.setZipCode(command.getAddress().getZipCode());
        address.setCity(command.getAddress().getCity());
        address.setStreet(command.getAddress().getStreet());
        address.setApartmentNumber(command.getAddress().getApartmentNumber());
        address.setFlatNumber(command.getAddress().getFlatNumber());
        personToUpdate.setAddress(address);
        parent.setPhoneNumber(command.getPhoneNumber());
    }


}
