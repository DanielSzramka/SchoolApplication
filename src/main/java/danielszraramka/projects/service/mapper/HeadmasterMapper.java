package danielszraramka.projects.service.mapper;

import danielszraramka.projects.model.Headmaster;
import danielszraramka.projects.model.User;
import danielszraramka.projects.service.command.CreateHeadmasterCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HeadmasterMapper {
    private PersonMapper personMapper;

    public Headmaster mapFromCreateHeadmasterCommand(CreateHeadmasterCommand command, User user) {


        return Headmaster.builder()
                .user(user)
//                .person()
                .startDate(command.getStartDate())
                .build();
    }


}
