package com.kennedy.example;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class FirstController {
	private final StudentRepository studentRepository;

	private final SchoolRepository schoolRepository;

	public FirstController(StudentRepository studentRepository, SchoolRepository schoolRepository) {
		this.studentRepository = studentRepository;
		this.schoolRepository = schoolRepository;
	}

	;


	@PostMapping("/students/create")
	public CreateStudentDto createStudent(
			@RequestBody CreateStudentDto studentDto) {
		School school = schoolRepository.findById(studentDto.schoolId())
				                .orElseThrow(() -> new RuntimeException("School not found"));

		var student = new Students(
				studentDto.firstName(),
				studentDto.lastName(),
				studentDto.email(),
				studentDto.age()
		);

		student.setSchool(school);
		student = studentRepository.save(student);

		return new CreateStudentDto(
				student.getFirstName(),
				student.getLastName(),
				student.getEmail(),
				student.getAge(),
				student.getSchool().getId()
		);
	}

	@GetMapping("/students/list")
	public List<ListStudentsDto> findAllStudents() {
		return studentRepository.findAll().stream()
				       .map(student -> new ListStudentsDto(
						       student.getFirstName(),
						       student.getLastName(),
						       student.getEmail(),
						       student.getAge(),
						       student.getSchool() != null ? student.getSchool().getName() : null
				       )).collect(Collectors.toList());
	}

	@GetMapping("/students/{student-id}")
	public Optional<Students> findByStudentId(@PathVariable("student-id") UUID studentId) {
		return studentRepository.findById(studentId);
	}

	@GetMapping("/students/name/{student-name}")
	public List<Students> findByStudentName(@PathVariable("student-name") String studentName) {
		return studentRepository.findByFirstName(studentName);
	}

	@DeleteMapping("/students/{student-id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteById(@PathVariable("student-id") UUID studentId) {
		studentRepository.deleteById(studentId);
		return;
	}
}
