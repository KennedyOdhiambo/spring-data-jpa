package com.kennedy.example;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SchoolController {
    private final SchoolRepository schoolRepository;

    public SchoolController(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    };

    @PostMapping("/school/create")
    public School createSchool(
            @RequestBody NewSchoolDto dto) {
        var school = new School(dto.name());
        return schoolRepository.save(school);
    }

    @GetMapping("/school/list")
    public List<ListSchoolsDto> listSchools() {
        return schoolRepository.findAll().stream()
                .map(school -> new ListSchoolsDto(school.getName()))
                .collect(Collectors.toList());
    }

}
