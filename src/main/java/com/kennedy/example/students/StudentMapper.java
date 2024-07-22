package com.kennedy.example.students;

import org.springframework.stereotype.Service;

import com.kennedy.example.school.School;

@Service
public class StudentMapper {

    public Students toStudent(CreateStudentDto studentDto, School school) {
        if (studentDto == null) {
            throw new NullPointerException("studentDto cannot be null");
        }

        if (school == null) {
            throw new NullPointerException("school cannot be null");
        }

        var student = new Students(
                studentDto.firstName(),
                studentDto.lastName(),
                studentDto.email(),
                studentDto.age());

        student.setSchool(school);

        return student;

    }
}
