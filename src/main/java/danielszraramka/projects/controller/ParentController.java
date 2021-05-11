package danielszraramka.projects.controller;

import danielszraramka.projects.controller.dto.ParentDto;
import danielszraramka.projects.controller.mapper.command.ParentCommandMapper;
import danielszraramka.projects.controller.mapper.dto.ParentDtoMapper;
import danielszraramka.projects.controller.request.CreateParentRequestAndUpdate;
import danielszraramka.projects.controller.validation.CreateGroupValidation;
import danielszraramka.projects.controller.validation.UpdateGroupValidation;
import danielszraramka.projects.service.ParentService;
import danielszraramka.projects.service.query.FindParentsQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/parents")
@RequiredArgsConstructor
public class ParentController {

    private final ParentService parentService;
    private final ParentCommandMapper parentCommandMapper;
    private final ParentDtoMapper parentDtoMapper;

    @PostMapping
    @ResponseStatus(CREATED)
    public void createParent(@RequestBody @Validated(CreateGroupValidation.class) CreateParentRequestAndUpdate request) {
        parentService.createParent(parentCommandMapper.mapFromCreateParentRequest(request));
    }

    @PutMapping("/{id}")
    public void updateParent(@PathVariable("id") Long id,
                             @RequestBody @Validated(UpdateGroupValidation.class) CreateParentRequestAndUpdate request) {
        parentService.updateParent(parentCommandMapper.mapFromCreateParentRequest(request, id));
    }

    @DeleteMapping("/{id}")
    public void removeParent(@PathVariable("id") Long id) {
        parentService.removeParent(id);
    }

    @GetMapping("/{id}")
    public ParentDto getParent(@PathVariable("id") Long id) {
        return parentDtoMapper.mapFromParent(parentService.getParent(id));
    }


    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<ParentDto> getParents(@RequestParam(value = "surname", required = false) String surname,
                                      @RequestParam(value = "name", required = false) String name,
                                      @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                      @RequestParam(value = "city", required = false) String city,
                                      @RequestParam(value = "street", required = false) String street,
                                      Authentication authentication) {

        FindParentsQuery findParentsQuery = FindParentsQuery.builder()
                .surname(surname)
                .name(name)
                .phoneNumber(phoneNumber)
                .city(city)
                .street(street)
                .build();

        return parentDtoMapper.mapFromParents(parentService.getParents(findParentsQuery));

    }

}
