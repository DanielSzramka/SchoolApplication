package danielszraramka.projects.controller;

import danielszraramka.projects.controller.dto.ErrorDto;
import danielszraramka.projects.controller.dto.ValidationErrorDto;
import danielszraramka.projects.service.exeption.PersonNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ControllerAdvice
@AllArgsConstructor
//"application/json; charset=utf-8"
public class ExceptionController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);
    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        //MessageSource bean validation
        List<ValidationErrorDto> errorDtos = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorDtos.add(ValidationErrorDto.builder()
                    .fieldName(fieldError.getField())
                    .errorMessage(messageSource.getMessage(fieldError, LocaleContextHolder.getLocale()))
                    .build());
        }

        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(errorDtos);

    }

//    @ExceptionHandler({PersonNotFoundException.class})
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public void handlePersonNotFoundException(PersonNotFoundException ex) {
//    }

    @ExceptionHandler({PersonNotFoundException.class})
    public ResponseEntity<ErrorDto> handlePersonNotFoundException(PersonNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.builder()
                .errorCode("404")
                .errorMessage(ex.getMessage())
                .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception ex) {
        logger.error("Internal server error.", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorDto.builder()
                .errorCode("500")
                .errorMessage("Internal server error.")
                .build());
    }
}
