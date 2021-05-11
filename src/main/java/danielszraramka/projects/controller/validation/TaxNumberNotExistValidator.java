package danielszraramka.projects.controller.validation;

import danielszraramka.projects.service.SchoolService;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class TaxNumberNotExistValidator implements ConstraintValidator<TaxNumberNotExist, String> {
    private final SchoolService schoolService;

    @Override
    public void initialize(TaxNumberNotExist constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return schoolService.isSchoolExist(value).isEmpty();
    }
}
