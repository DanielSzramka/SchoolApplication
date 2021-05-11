package danielszraramka.projects.service.exeption;

public class SchoolNotFoundException extends RuntimeException {
    public SchoolNotFoundException() {
        super("School not found!");
    }
}
