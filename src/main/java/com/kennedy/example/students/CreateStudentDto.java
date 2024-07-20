package com.kennedy.example.students;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateStudentDto(
		@NotBlank(message = "First name is required") @Size(min = 2, message = "First name must be atleast 2 characters") String firstName,

		@NotBlank(message = "Lat name is required") @Size(min = 2, message = "Last name should be atleast 2 characters") String lastName,

		@Email(message = "INvalid email format") String email,

		@Min(value = 0, message = "Age must be a non-negative number") int age,

		UUID schoolId) {

}
