package danielszraramka.projects.service.command;

import danielszraramka.projects.controller.mapper.command.AddressCommandMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class CreateAddressCommand extends AddressCommandMapper {

    private final Long id;
    private final String zipCode;
    private final String city;
    private final String street;
    private final String apartmentNumber;
    private final String flatNumber;
}
