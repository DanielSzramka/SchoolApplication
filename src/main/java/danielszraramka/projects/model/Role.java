package danielszraramka.projects.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
