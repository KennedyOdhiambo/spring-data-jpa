package com.kennedy.example.students;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kennedy.example.school.School;
import com.kennedy.example.school.SchoolRepository;

@Service
public class StudentsService {

    private final SchoolRepository schoolRepository;
    private final StudentRepository studentRepository;

    public StudentsService(SchoolRepository schoolRepository, StudentRepository studentRepository) {
        this.schoolRepository = schoolRepository;
        this.studentRepository = studentRepository;
    }

    public CreateStudentDto createStudent(CreateStudentDto studentDto) {
        School school = schoolRepository.findById(studentDto.schoolId())
                .orElseThrow(() -> new RuntimeException("School not found"));

        var student = new Students(
                studentDto.firstName(),
                studentDto.lastName(),
                studentDto.email(),
                studentDto.age());

        student.setSchool(school);
        student = studentRepository.save(student);
        return new CreateStudentDto(
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getAge(),
                student.getSchool().getId());
    }

    public List<ListStudentsDto> listStudents() {
        return studentRepository.findAll().stream()
                .map(student -> new ListStudentsDto(
                        student.getFirstName(),
                        student.getLastName(),
                        student.getEmail(),
                        student.getAge(),
                        student.getSchool() != null ? student.getSchool().getName() : null))
                .collect(Collectors.toList());
    }

    public Optional<Students> getStudentById(UUID studentId) {
        return studentRepository.findById(studentId);
    }

    public void deleteStudent(UUID studentId) {
        studentRepository.deleteById(studentId);
        return;
    }
}
