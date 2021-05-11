package danielszraramka.projects.controller.validation;

import danielszraramka.projects.service.UserService;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class EmailNotExistValidator implements ConstraintValidator<EmailNotExist, String> {
    private final UserService userService;

    @Override
    public void initialize(EmailNotExist constraintAnnotation) {

    }

    @Override
    public boolean isValid(String mailField, ConstraintValidatorContext constraintValidatorContext) {
        return userService.findUserByMail(mailField).isEmpty();
    }
}
