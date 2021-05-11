package danielszraramka.projects.service.mapper;

import danielszraramka.projects.service.command.CreateAddressCommand;
import org.springframework.stereotype.Component;

@Component("serviceAddressMapper")
public class AddressMapper {

    public danielszraramka.projects.model.Address mapFromCreateAddressCommand(CreateAddressCommand command) {
        return danielszraramka.projects.model.Address.builder()
                .addressId(command.getId())
                .zipCode(command.getZipCode())
                .city(command.getCity())
                .street(command.getCity())
                .apartmentNumber(command.getApartmentNumber())
                .flatNumber(command.getFlatNumber())
                .build();

    }


}
