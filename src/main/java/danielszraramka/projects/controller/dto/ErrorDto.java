package danielszraramka.projects.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorDto {
    private String errorCode;
    private String errorMessage;
}
