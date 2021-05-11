package danielszraramka.projects.service.exeption;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException() {
        super("Person not found!");
    }
}
