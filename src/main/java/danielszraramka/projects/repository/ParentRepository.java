package danielszraramka.projects.repository;

import danielszraramka.projects.model.Parent;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long>, JpaSpecificationExecutor<Parent> {
    @Query("select pa from Parent pa JOIN pa.person pe where pe.surname = :surname")
    List<Parent> findAllBySurname(@Param("surname") String surname);

    @Override
    @EntityGraph(attributePaths = {"person", "person.address"})
    List<Parent> findAll(@Nullable Specification<Parent> var1);
}
