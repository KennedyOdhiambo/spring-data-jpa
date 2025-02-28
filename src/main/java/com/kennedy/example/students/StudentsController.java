package com.kennedy.example.students;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kennedy.example.school.SchoolRepository;

import jakarta.validation.Valid;

@RestController
public class StudentsController {
    private final StudentsService studentsService;

    public StudentsController(StudentsService studentsService, SchoolRepository schoolRepository,
            StudentRepository studentRepository) {
        this.studentsService = studentsService;
    }

    @PostMapping("/students/create")
    public CreateStudentDto createStudent(
            @Valid @RequestBody CreateStudentDto studentDto) {
        return this.studentsService.createStudent(studentDto);
    }

    @GetMapping("/students/list")
    public List<ListStudentsDto> findAllStudents() {
        return this.studentsService.listStudents();
    }

    @GetMapping("/students/{student-id}")
    public Optional<Students> findByStudentId(@PathVariable("student-id") UUID studentId) {
        return this.studentsService.getStudentById(studentId);
    }

    @DeleteMapping("/students/{student-id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable("student-id") UUID studentId) {
        this.studentsService.deleteStudent(studentId);
        return;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exp) {
        var errors = new HashMap<String, String>();
        exp.getBindingResult().getAllErrors()
                .forEach(err -> {
                    var fieldName = ((FieldError) err).getField();
                    var errorMessage = err.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
