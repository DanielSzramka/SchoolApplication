package danielszraramka.projects.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "persons")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long personId;
    private String name;

    @Column(name = "second_name")
    private String secondName;
    private String surname;
    @Column(name = "identity_number")
    private String identityNumber;
    private String citizenship;
    private String nationality;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
}
