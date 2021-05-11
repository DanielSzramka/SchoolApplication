package danielszraramka.projects.controller.mapper.command;

import danielszraramka.projects.controller.request.AddressRequest;
import danielszraramka.projects.controller.request.CreateParentRequestAndUpdate;
import danielszraramka.projects.service.command.CreateAddressCommand;
import danielszraramka.projects.service.command.CreateParentCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParentCommandMapperTest {

    private static final long ID_ADDRESS_DTO = 12l;
    private static final String ZIP_CODE = "50-321";
    private static final String CITY = "Wrocław";
    private static final String STREET = "Krakowska";
    private static final String FLAT_NUMBER = "12";
    private static final String APARTMENT_NUMBER = "43";
    private static final long ID_CREATE_PARENT_REQUEST_AND_UPDATE = 5L;
    private static final String MAIL = "Jan@gmail.com";
    private static final String PASSWORD = "password";
    private static final String NAME = "Jan";
    private static final String SECOND_NAME = "Franek";
    private static final String SURNAME = "Kowalski";
    private static final String NATIONALITY = "Polska";
    private static final String CITIZENSHIP = "Polska";
    private static final String IDENTITY_NUMBER = "1234567";
    private static final String PHONE_NUMBER = "123098456";


    @InjectMocks
    ParentCommandMapper parentCommandMapper;

    @Mock
    private AddressCommandMapper addressMapper;


    @Test
    public void shouldReturnParentCommandMapper() {
        //given
        CreateAddressCommand createAddressCommand = addressMapperTest();
        AddressRequest addressRequest = addressRequestTest();
        CreateParentRequestAndUpdate createParentRequestAndUpdateTest = createParentRequestANdUpdate(addressRequest);

        CreateParentCommand command = CreateParentCommandTest(createAddressCommand);
        when(addressMapper.mapFromCreateAddressRequest(eq(addressRequest))).thenReturn(createAddressCommand);
        //when
        CreateParentCommand createParentCommand = parentCommandMapper.mapFromCreateParentRequest(createParentRequestAndUpdateTest);
        //then
        assertThat(createParentCommand.getSurname()).isEqualTo(command.getSurname());
        assertThat(createParentCommand.getName()).isEqualTo(command.getName());
        assertThat(createParentCommand.getSecondName()).isEqualTo(command.getSecondName());
        assertThat(createParentCommand.getNationality()).isEqualTo(command.getNationality());
        assertThat(createParentCommand.getAddress().getZipCode()).isEqualTo(command.getAddress().getZipCode());
        assertThat(createParentCommand.getAddress().getCity()).isEqualTo(command.getAddress().getCity());
        assertThat(createParentCommand.getAddress().getStreet()).isEqualTo(command.getAddress().getStreet());
        assertThat(createParentCommand.getAddress().getFlatNumber()).isEqualTo(command.getAddress().getFlatNumber());
        assertThat(createParentCommand.getAddress().getApartmentNumber()).isEqualTo(command.getAddress().getApartmentNumber());
        assertThat(createParentCommand.getPhoneNumber()).isEqualTo(command.getPhoneNumber());

    }

    private CreateParentCommand CreateParentCommandTest(CreateAddressCommand createAddressCommand) {
        return CreateParentCommand.builder()
                .mail(MAIL)
                .password(PASSWORD)
                .name(NAME)
                .surname(SURNAME)
                .secondName(SECOND_NAME)
                .address(createAddressCommand)
                .nationality(NATIONALITY)
                .citizenship(CITIZENSHIP)
                .phoneNumber(PHONE_NUMBER)
                .build();
    }


    private AddressRequest addressRequestTest() {
        return AddressRequest.builder()
                .zipCode(ZIP_CODE)
                .city(CITY)
                .street(STREET)
                .apartmentNumber(APARTMENT_NUMBER)
                .flatNumber(FLAT_NUMBER)
                .build();
    }

    private CreateParentRequestAndUpdate createParentRequestANdUpdate(AddressRequest addressRequest) {

        return CreateParentRequestAndUpdate.builder()
                .mail(MAIL)
                .password(PASSWORD)
                .name(NAME)
                .secondName(SECOND_NAME)
                .surname(SURNAME)
                .identityNumber(IDENTITY_NUMBER)
                .nationality(NATIONALITY)
                .citizenship(CITIZENSHIP)
                .address(addressRequest)
                .phoneNumber(PHONE_NUMBER)
                .build();
    }

    private CreateAddressCommand addressMapperTest() {
        return CreateAddressCommand.builder()
                .zipCode(ZIP_CODE)
                .city(CITY)
                .street(STREET)
                .apartmentNumber(APARTMENT_NUMBER)
                .flatNumber(FLAT_NUMBER)
                .build();
    }


}