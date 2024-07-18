package com.kennedy.example;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Students, UUID> {

    List<Students> findByFirstName(String firstName);
}
