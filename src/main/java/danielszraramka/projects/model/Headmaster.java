package danielszraramka.projects.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "headmasters")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Headmaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "headmaster_id")
    private Long headmasterId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "school_id")
    private School school;

    @Column(name = "start_date")
    private Date startDate;
}
