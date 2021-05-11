package danielszraramka.projects.model;

import lombok.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.*;

@Entity
@Table(name = "schools")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_id")
    private Long schoolId;
    @Column(name = "tax_number")
    private String taxNumber;
    @Column(name = "full_name")
    private String fullNameOfSchool;
    @Column(name = "short_name")
    private String shortName;
    private String type;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String mail;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    public static Specification<School> hasTaxNumber(String taxNumber) {
        return (school, cq, cb) -> cb.equal(school.get("taxNumber"), taxNumber);
    }

    public static Specification<School> hasFullNameOfSchool(String fullNameOfSchool) {
        return (school, cq, cb) -> cb.equal(school.get("fullNameOfSchool"), fullNameOfSchool);
    }

    public static Specification<School> hasShortName(String shortName) {
        return (school, cq, cb) -> cb.equal(school.get("shortName"), shortName);
    }

    public static Specification<School> hasType(String type) {
        return (school, cq, cb) -> cb.equal(school.get("type"), type);
    }

    public static Specification<School> hasCity(String city) {
        return (school, cq, cb) -> cb.equal(school.get("address").get("city"), city);
    }

    public static Specification<School> hasStreet(String street) {
        return (school, cq, cb) -> cb.equal(school.get("address").get("street"), street);
    }
}

