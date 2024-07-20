package com.kennedy.example.school;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
public class SchoolController {

    private final SchoolServices schoolServices;

    public SchoolController(SchoolServices schoolServices) {

        this.schoolServices = schoolServices;
    };

    @PostMapping("/school/create")
    public School createSchool(
            @RequestBody NewSchoolDto dto) {
        return this.schoolServices.createSchool(dto);
    }

    @GetMapping("/school/list")
    public List<ListSchoolsDto> listSchools() {
        return this.schoolServices.listSChools();
    }

}
