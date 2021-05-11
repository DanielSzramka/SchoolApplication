package danielszraramka.projects.controller.mapper.command;

import danielszraramka.projects.controller.request.AddressRequest;
import danielszraramka.projects.service.command.CreateAddressCommand;
import org.springframework.stereotype.Component;

@Component("controllerAddressMapper")
public class AddressCommandMapper {
    public CreateAddressCommand mapFromCreateAddressRequest(AddressRequest addressRequest) {
        return CreateAddressCommand.builder()
                .id(addressRequest.getIdAddressDto())
                .zipCode(addressRequest.getZipCode())
                .city(addressRequest.getCity())
                .street(addressRequest.getStreet())
                .apartmentNumber(addressRequest.getApartmentNumber())
                .flatNumber(addressRequest.getFlatNumber())
                .build();
    }


}
