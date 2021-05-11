package danielszraramka.projects.service;

import danielszraramka.projects.model.User;
import danielszraramka.projects.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    //Create i Update remove
    private final UserRepository userRepository;

    public Optional<User> findUserByMail(String mail) {
        return userRepository.findUserByMail(mail);
    }

    //ToDo:  finish this class;
    public void createUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(User user) {

    }

    public void removeUser(Long id) {

    }

    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
