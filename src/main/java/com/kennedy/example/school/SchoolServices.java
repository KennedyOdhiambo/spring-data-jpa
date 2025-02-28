package com.kennedy.example.school;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class SchoolServices {
    private final SchoolRepository schoolRepository;

    public SchoolServices(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public School createSchool(NewSchoolDto dto) {
        var school = new School(dto.name());
        return schoolRepository.save(school);
    }

    public List<ListSchoolsDto> listSChools() {
        return schoolRepository.findAll().stream()
                .map(school -> new ListSchoolsDto(school.getName()))
                .collect(Collectors.toList());
    }

}
