package com.kennedy.example;

import java.util.UUID;

public record CreateStudentDto(
		String firstName,
		String lastName,
		String email,
		int age,
		UUID schoolId
) {
}
