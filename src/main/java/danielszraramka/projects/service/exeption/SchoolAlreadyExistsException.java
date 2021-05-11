package danielszraramka.projects.service.exeption;

public class SchoolAlreadyExistsException extends RuntimeException {
    public SchoolAlreadyExistsException() {
        super("School Already Exist");
    }
}
