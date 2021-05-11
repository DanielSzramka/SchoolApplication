package danielszraramka.projects.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "zip_code")
    private String zipCode;

    private String city;
    private String street;

    @Column(name = "apartment_number")
    private String apartmentNumber;

    @Column(name = "flat_number")
    private String flatNumber;
}
