package com.example.exceptionhandling.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.exceptionhandling.dto.StudentDto;
import com.example.exceptionhandling.exception.DublicateStudentException;
import com.example.exceptionhandling.exception.StudentNotFoundException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@Validated
public class StudentController {
	
	@GetMapping("/test")
	public String test() {
		
		int result = 10 / 0;
		
		return "Result :" + result;
		
	}
	
//	@ExceptionHandler(ArithmeticException.class)
//	public ResponseEntity<String> handleArithmeticException(
//	        ArithmeticException ex) {
//
//	    return ResponseEntity
//	            .status(HttpStatus.BAD_REQUEST)
//	            .body("Arithmetic operation is invalid");
//	}
	
	@GetMapping("/student")
	public String getStudent() {
		
		throw new StudentNotFoundException(
				"Student not Found"
				);
		
	}
	
	
//	Approach 2 — ResponseStatusException
//	@GetMapping("/student")
//	public String getStudent() {
//
//	    throw new ResponseStatusException(
//	            HttpStatus.NOT_FOUND,
//	            "Student not found"
//	    );
//	}
	
	
//	@ExceptionHandler(StudentNotFoundException.class)
//	public ResponseEntity<String> handleStudentNotFound(StudentNotFoundException ex){
//		
//		return ResponseEntity.status(HttpStatus.NOT_FOUND)
//								.body(ex.getMessage());
//		
//	}
	
	@GetMapping("/dublicate")
	public String dublicateStudent() {
		throw new DublicateStudentException("Student already exists");
	}
	
//	@ExceptionHandler(DublicateStudentException.class)
//	public ResponseEntity<String> handleDublicateStudent(DublicateStudentException ex){
//		
//		return ResponseEntity.status(HttpStatus.CONFLICT)
//								.body(ex.getMessage());
//		
//	}
	
	
	@PostMapping(value = "/student",consumes = "application/json")
	public String createStudent(@Valid @RequestBody StudentDto dto) {
		
		return "Student Created";
		
	}
	
	@GetMapping("/students/search")
	public String searchStudent(@RequestParam
			@Min(value = 1,message = "Page must be atleast 1") Integer page) {
		
		return "Searching page:" + page;
		
	}
	
	@GetMapping("/students/{id}")
	public String getStudent(
	        @PathVariable
	        @Min(
	            value = 1,
	            message = "Student ID must be greater than 0"
	        )
	        Long id) {

	    return "Student ID: " + id;
	}
	
	@GetMapping("/students/test")
	public String tests() {

	    String name = null;

	    return name.toUpperCase();
	}
	

}
