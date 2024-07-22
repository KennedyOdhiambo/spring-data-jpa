package com.kennedy.example.students;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import com.kennedy.example.school.School;
import com.kennedy.example.school.SchoolRepository;

public class StudentsServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private SchoolRepository schoolRepository;

    @Mock
    private StudentMapper studentMapper;

    private StudentsService studentsService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        studentsService = new StudentsService(studentRepository, schoolRepository, studentMapper);
    }

    @Test
    void createStudent_shouldCreateAndReturnStudent() {
        CreateStudentDto inputDto = new CreateStudentDto(
                "Kennedy",
                "Odhiambo",
                "x.johnkennedy.x@gmail.com",
                26,
                UUID.randomUUID());

        School school = new School("Test school");
        school.setId(inputDto.schoolId());

        Students student = new Students(
                "Kennedy",
                "Odhiambo",
                "x.johnkennedy.x@gmail.com",
                26);
        student.setSchool(school);

        Students savedStudent = new Students(
                "Kennedy",
                "Odhiambo",
                "x.johnkennedy.x@gmail.com",
                26);
        savedStudent.setSchool(school);

        when(schoolRepository.findById(inputDto.schoolId())).thenReturn(Optional.of(school));
        when(studentMapper.toStudent(inputDto, school)).thenReturn(student);
        when(studentRepository.save(student)).thenReturn(savedStudent);

        CreateStudentDto result = studentsService.createStudent(inputDto);

        assertNotNull(result);
        assertEquals(savedStudent.getFirstName(), result.firstName());
        assertEquals(savedStudent.getLastName(), result.lastName());
        assertEquals(savedStudent.getEmail(), result.email());
        assertEquals(savedStudent.getAge(), result.age());
        assertEquals(savedStudent.getSchool().getId(), result.schoolId());

        verify(schoolRepository).findById(inputDto.schoolId());
        verify(studentMapper, times(1)).toStudent(inputDto, school);
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void should_return_list_of_students() {
        List<Students> studentsList = Arrays.asList(new Students());

        when(studentRepository.findAll()).thenReturn(studentsList);

        List<ListStudentsDto> result = studentsService.listStudents();

        assertNotNull(result);
        assertEquals(studentsList.size(), result.size());

        verify(studentRepository).findAll();
    }

    @Test
    void should_return_student_with_specified_id() {
        UUID studentId = UUID.randomUUID();

        var student = new Students(
                "Kennedy",
                "Odhiambo",
                "x.johnkennedy.x@gmail.com",
                26);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        Optional<Students> result = studentsService.getStudentById(studentId);

        assertTrue(result.isPresent());
        assertEquals(student, result.get());
    }

    @Test
    void should_return_emptry_when_student_not_found() {
        UUID studentId = UUID.randomUUID();
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        Optional<Students> result = studentsService.getStudentById(studentId);

        assertTrue(result.isEmpty());
        verify(studentRepository).findById(studentId);
    }

    @Test
    void should_delete_student() {
        UUID studentId = UUID.randomUUID();
        studentsService.deleteStudent(studentId);

        verify(studentRepository).deleteById(studentId);
    }
}
