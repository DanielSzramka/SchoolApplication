package danielszraramka.projects.service;

import danielszraramka.projects.repository.HeadmasterRepository;
import danielszraramka.projects.service.command.CreateHeadmasterCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class HeadmasterService {
    private HeadmasterRepository headmasterRepository;

    public void createHeadmaster(CreateHeadmasterCommand createHeadmasterCommand) {

    }
}
