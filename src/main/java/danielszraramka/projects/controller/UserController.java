package danielszraramka.projects.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/users")
public class UserController {
    @PostMapping("/login")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(OK)
    public void login() {
    }

    @PostMapping("/logout-success")
    @ResponseStatus(OK)
    public void logoutSuccess() {
    }
}
