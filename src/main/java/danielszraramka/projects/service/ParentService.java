package danielszraramka.projects.service;

import danielszraramka.projects.model.Parent;
import danielszraramka.projects.model.User;
import danielszraramka.projects.repository.ParentRepository;
import danielszraramka.projects.repository.UserRepository;
import danielszraramka.projects.service.command.CreateParentCommand;
import danielszraramka.projects.service.exeption.EmailAlreadyExistsException;
import danielszraramka.projects.service.exeption.PersonNotFoundException;
import danielszraramka.projects.service.mapper.ParentMapper;
import danielszraramka.projects.service.mapper.UserMapper;
import danielszraramka.projects.service.query.FindParentsQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static danielszraramka.projects.model.Parent.*;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
@RequiredArgsConstructor
@Transactional
public class ParentService {

    private final ParentRepository parentRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ParentMapper parentMapper;


    //Create, Update, Remove
    public void createParent(CreateParentCommand command) {
        if (userWithEmailNotExist(command.getMail())) {
            User user = userMapper.mapFromCreateParentCommand(command);
            userRepository.save(user);
            parentRepository.save(parentMapper.mapFromCreateParentCommandAndUser(command, user));
        } else {
            throw new EmailAlreadyExistsException(command.getMail());
        }
    }

    public void updateParent(CreateParentCommand command) {
        Optional<Parent> parentToUpdate = parentRepository.findById(command.getIdCreateParentCommand());
        if (parentToUpdate.isPresent()) {
            parentMapper.updateParent(command, parentToUpdate.get());
        } else {
            throw new PersonNotFoundException();
        }
    }


    public void removeParent(Long id) {
        Optional<Parent> parent = parentRepository.findById(id);
        if (parent.isPresent()) {
            Long userId = parent.get().getUser().getId();
            parentRepository.deleteById(id);
            //TODO : teacher and parent assigned to one user???
            userRepository.deleteById(userId);
        } else {
            throw new PersonNotFoundException();
        }
    }

    public Parent getParent(Long id) {
        return parentRepository.findById(id).orElseThrow(PersonNotFoundException::new);
    }

    public List<Parent> getParents(FindParentsQuery findParentsQuery) {

        List<Specification<Parent>> specifications = getSpecifications(findParentsQuery);
        Specification<Parent> finalSpecification;

        if (specifications.size() > 0) {
            finalSpecification = where(specifications.get(0));
            for (int i = 1; i < specifications.size(); i++) {
                finalSpecification = finalSpecification.and(specifications.get(i));
            }
            return parentRepository.findAll(finalSpecification);
        }
        return parentRepository.findAll();
    }

    private boolean userWithEmailNotExist(String mail) {
        return userRepository.findUserByMail(mail).isEmpty();
    }

    private List<Specification<Parent>> getSpecifications(FindParentsQuery findParentsQuery) {
        List<Specification<Parent>> specifications = new ArrayList<>();

        if (isNotBlank(findParentsQuery.getSurname())) {
            specifications.add(hasSurname(findParentsQuery.getSurname()));
        }
        if (isNotBlank(findParentsQuery.getName())) {
            specifications.add(hasName(findParentsQuery.getName()));
        }
        if (isNotBlank(findParentsQuery.getPhoneNumber())) {
            specifications.add(hasPhoneNumber(findParentsQuery.getPhoneNumber()));
        }
        if (isNotBlank(findParentsQuery.getCity())) {
            specifications.add(hasCity(findParentsQuery.getCity()));
        }
        if (isNotBlank(findParentsQuery.getStreet())) {
            specifications.add(hasStreet(findParentsQuery.getStreet()));
        }
        return specifications;
    }
}
