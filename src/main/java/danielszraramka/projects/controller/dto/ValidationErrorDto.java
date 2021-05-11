package danielszraramka.projects.controller.dto;

import lombok.Builder;
import lombok.Setter;


@Setter
@Builder
public class ValidationErrorDto {
    private String fieldName;
    private String errorMessage;
}
