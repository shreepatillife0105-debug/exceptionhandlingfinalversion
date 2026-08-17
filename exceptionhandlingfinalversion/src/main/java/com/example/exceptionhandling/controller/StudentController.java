package com.example.exceptionhandling.controller;
import com.example.exceptionhandling.exception.StudentNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Example controller.
 *
 * Notice:
 *
 * NO try/catch.
 *
 * NO exception response creation.
 *
 * GlobalExceptionHandler handles exceptions.
 */
@RestController
@RequestMapping("/students")
public class StudentController {

	@GetMapping("/{id}")
    public ResponseEntity<String> getStudent(
            @PathVariable Long id
    ) {

        if (id == 100L) {

            throw new StudentNotFoundException(
                    "Student with id "
                            + id
                            + " was not found"
            );
        }

        return ResponseEntity.ok(
                "Student found"
        );
    }
	
}
