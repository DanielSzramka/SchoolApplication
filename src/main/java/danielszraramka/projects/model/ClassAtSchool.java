package danielszraramka.projects.model;

import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@Table(name = "classes")
public class ClassAtSchool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long classId;
    @Column(name = "class_name")
    private final String className;
}
