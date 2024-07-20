package com.kennedy.example;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "school")
public class School {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String name;

	@OneToMany(mappedBy = "school")
	@JsonIgnore
	private List<Students> students;

	public School() {
	}

	public School(String name) {

		this.name = name;
	}

	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Students> getStudents() {
		return this.students;
	}

	public void setStudents(List<Students> students) {
		this.students = students;
	}

}
