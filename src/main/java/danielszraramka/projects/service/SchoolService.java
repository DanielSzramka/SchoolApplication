package danielszraramka.projects.service;

import danielszraramka.projects.model.School;
import danielszraramka.projects.repository.SchoolRepository;
import danielszraramka.projects.service.command.CreateSchoolCommand;
import danielszraramka.projects.service.exeption.SchoolAlreadyExistsException;
import danielszraramka.projects.service.exeption.SchoolNotFoundException;
import danielszraramka.projects.service.mapper.SchoolMapper;
import danielszraramka.projects.service.query.FindSchoolsQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static danielszraramka.projects.model.School.*;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
@RequiredArgsConstructor
@Transactional
public class SchoolService {
    private final SchoolRepository schoolRepository;
    private final SchoolMapper schoolMapper;

    //Create, Update, Remove
    public void createSchool(CreateSchoolCommand createSchoolCommand) {
        if (isSchoolExist(createSchoolCommand.getTaxNumber()).isEmpty()) {
            schoolRepository.save(schoolMapper.mapFromCreateSchoolCommand(createSchoolCommand));
        } else {
            throw new SchoolAlreadyExistsException();
        }
    }

    public Optional<School> isSchoolExist(String taxNumber) {
        return schoolRepository.findSchoolByTaxNumber(taxNumber);
    }

    public void updateSchool(CreateSchoolCommand createSchoolCommand) {
        Optional<School> school = schoolRepository.findById(createSchoolCommand.getIdCreateSchoolCommand());
        if (school.isPresent()) {
            schoolMapper.updateSchool(createSchoolCommand, school.get());

        } else {
            throw new SchoolNotFoundException();
        }
    }

    public School getSchool(long id) {
        return schoolRepository.findById(id).orElseThrow(SchoolNotFoundException::new);
    }

    public void removeSchool(long id) {
        Optional<School> school = schoolRepository.findById(id);
        if (school.isPresent()) {
            schoolRepository.deleteById(id);
        } else {
            throw new SchoolNotFoundException();
        }
    }

    //FindSchoolsQuery
    public List<School> getSchools(FindSchoolsQuery findSchoolsQuery) {
        List<Specification<School>> specifications = getSpecifications(findSchoolsQuery);
        Specification<School> finalSpecification;
        if (specifications.size() > 0) {
            finalSpecification = where(specifications.get(0));
            for (int i = 1; i < specifications.size(); i++) {
                finalSpecification = finalSpecification.and(specifications.get(i));
            }
            return schoolRepository.findAll(finalSpecification);
        }
        return schoolRepository.findAll();
    }

    private List<Specification<School>> getSpecifications(FindSchoolsQuery findSchoolsQuery) {
        List<Specification<School>> specifications = new ArrayList<>();

        if (isNotBlank(findSchoolsQuery.getTaxNumber())) {
            specifications.add(hasTaxNumber(findSchoolsQuery.getTaxNumber()));
        }
        if (isNotBlank(findSchoolsQuery.getFullNameOfSchool())) {
            specifications.add(hasFullNameOfSchool(findSchoolsQuery.getFullNameOfSchool()));
        }
        if (isNotBlank(findSchoolsQuery.getShortName())) {
            specifications.add(hasShortName(findSchoolsQuery.getShortName()));
        }
        if (isNotBlank(findSchoolsQuery.getType())) {
            specifications.add(hasType(findSchoolsQuery.getType()));
        }
        if (isNotBlank(findSchoolsQuery.getCity())) {
            specifications.add(hasCity(findSchoolsQuery.getCity()));
        }
        if (isNotBlank(findSchoolsQuery.getStreet())) {
            specifications.add(hasStreet(findSchoolsQuery.getStreet()));
        }
        return specifications;
    }


}
