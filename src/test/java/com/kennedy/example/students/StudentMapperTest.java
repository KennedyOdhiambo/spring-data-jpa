package com.kennedy.example.students;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kennedy.example.school.School;

public class StudentMapperTest {

    private StudentMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new StudentMapper();
    }

    @Test
    @DisplayName("toStudent maps correctly with valid inputs")
    void testToStudent() {

        var schoolId = UUID.fromString("da07d29c-90bc-4709-8b9a-0f4333e3fd48");
        var dto = new CreateStudentDto(
                "Kennedy",
                "Odhiambo",
                "x.johnkennedy.x@gmail.com",
                16,
                schoolId);

        var school = new School("Test school");
        school.setId(schoolId);

        var student = mapper.toStudent(dto, school);

        assertAll(
                () -> assertEquals(dto.firstName(), student.getFirstName()),
                () -> assertEquals(dto.lastName(), student.getLastName()),
                () -> assertEquals(dto.email(), student.getEmail()),
                () -> assertEquals(dto.age(), student.getAge()),
                () -> assertEquals(dto.schoolId(), student.getSchool().getId()));

    }

    @Test
    @DisplayName("toStudent throws NullPointerException when studentDto is null")
    void toStudentThrowsForNullDto() {
        var school = new School("Test School");

        var exception = assertThrows(NullPointerException.class, () -> mapper.toStudent(null, school));
        assertEquals("studentDto cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("toStudent throws NullPointerException when school is null")
    void toStudentThrowsForNullSchool() {
        var dto = new CreateStudentDto(
                "Kennedy",
                "Odhiambo",
                "x.johnkennedy.x@gmail.com",
                26,
                UUID.randomUUID());

        var exception = assertThrows(NullPointerException.class, () -> mapper.toStudent(dto, null));
        assertEquals("school cannot be null", exception.getMessage());
    }
}
