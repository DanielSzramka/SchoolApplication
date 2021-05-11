package danielszraramka.projects.controller;

import danielszraramka.projects.controller.dto.SchoolDto;
import danielszraramka.projects.controller.mapper.command.SchoolCommandMapper;
import danielszraramka.projects.controller.mapper.dto.SchoolDtoMapper;
import danielszraramka.projects.controller.request.CreateSchoolRequestAndUpdate;
import danielszraramka.projects.controller.validation.CreateGroupValidation;
import danielszraramka.projects.controller.validation.UpdateGroupValidation;
import danielszraramka.projects.service.SchoolService;
import danielszraramka.projects.service.query.FindSchoolsQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schools")
@RequiredArgsConstructor
public class SchoolController {
    private final SchoolService schoolService;
    private final SchoolCommandMapper schoolCommandMapper;
    private final SchoolDtoMapper schoolDtoMapper;

    @PostMapping
    public void createSchool(@RequestBody @Validated(CreateGroupValidation.class) CreateSchoolRequestAndUpdate createSchoolRequest) {
        schoolService.createSchool(schoolCommandMapper.mapFromCreateSchoolRequest(createSchoolRequest));
    }

    @GetMapping("/{id}")
    public SchoolDto getSchool(@PathVariable("id") Long id) {
        schoolService.getSchool(id);
        return schoolDtoMapper.mapFromSchool(schoolService.getSchool(id));
    }

    @DeleteMapping("/{id}")
    public void removeSchool(@PathVariable("id") Long id) {
        schoolService.removeSchool(id);
    }

    @GetMapping
    public List<SchoolDto> getParents(@RequestParam(value = "taxNumber", required = false) String taxNumber,
                                      @RequestParam(value = "fullNameOfSchool", required = false) String fullNameOfSchool,
                                      @RequestParam(value = "shortName", required = false) String shortName,
                                      @RequestParam(value = "type", required = false) String type,
                                      @RequestParam(value = "city", required = false) String city,
                                      @RequestParam(value = "street", required = false) String street) {
        FindSchoolsQuery build = FindSchoolsQuery.builder()
                .taxNumber(taxNumber)
                .fullNameOfSchool(fullNameOfSchool)
                .shortName(shortName)
                .type(type)
                .city(city)
                .street(street)
                .build();

        return schoolDtoMapper.mapFromSchools(schoolService.getSchools(build));
    }

    @PutMapping("/{id}")
    public void updateSchool(@PathVariable("id") Long id, @RequestBody @Validated(UpdateGroupValidation.class) CreateSchoolRequestAndUpdate createSchoolRequest) {
        schoolService.updateSchool(schoolCommandMapper.mapFromCreateSchoolRequest(createSchoolRequest, id));
    }
}
