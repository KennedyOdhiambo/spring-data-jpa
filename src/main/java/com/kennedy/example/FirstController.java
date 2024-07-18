package com.kennedy.example;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
    private final StudentRepository studentRepository;

    public FirstController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    };

    @GetMapping("/hello")
    public String hello() {
        return "Hello from my controller";
    }

    @PostMapping("/students/create")
    public Students post(@RequestBody Students students) {
        return studentRepository.save(students);
    }

    @GetMapping("/students/list")
    public List<Students> findAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/students/{student-id}")
    public Optional<Students> findByStudentId(
            @PathVariable("student-id") UUID studentId) {
        return studentRepository.findById(studentId);
    }

    @GetMapping("/students/name/{student-name}")
    public List<Students> findByStudentName(
            @PathVariable("student-name") String studentName) {
        return studentRepository.findByFirstName(studentName);
    }

    @DeleteMapping("/students/{student-id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(
            @PathVariable("student-id") UUID studentId) {
        studentRepository.deleteById(studentId);
        return;
    }
}
