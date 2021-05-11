package danielszraramka.projects.controller.request;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class AddressRequest {
    private Long idAddressDto;
    private String zipCode;
    private String city;
    private String street;
    private String apartmentNumber;
    private String flatNumber;
}
