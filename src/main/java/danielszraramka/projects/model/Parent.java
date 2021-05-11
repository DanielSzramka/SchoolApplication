package danielszraramka.projects.model;

import lombok.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.*;

@Entity
@Table(name = "parents")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parent_id")
    private Long parentId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "phone_number")
    private String phoneNumber;

    public static Specification<Parent> hasSurname(String surname) {
        return (parent, cq, cb) -> cb.equal(parent.get("person").get("surname"), surname);
    }

    public static Specification<Parent> hasName(String name) {
        return (parent, cq, cb) -> cb.equal(parent.get("person").get("name"), name);
    }

    public static Specification<Parent> hasPhoneNumber(String phoneNumber) {
        return (parent, cq, cb) -> cb.equal(parent.get("phoneNumber"), phoneNumber);
    }

    public static Specification<Parent> hasCity(String city) {
        return (parent, cq, cb) -> cb.equal(parent.get("person").get("address").get("city"), city);
    }

    public static Specification<Parent> hasStreet(String street) {
        return (parent, cq, cb) -> cb.equal(parent.get("person").get("address").get("street"), street);
    }
}
