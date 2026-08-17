package com.example.exceptionhandling.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.exceptionhandling.exception.StudentNotFoundException;

@RestController
public class TeacherController {

	@GetMapping("/teacher")
	public String getTeacher() {
		
		throw new StudentNotFoundException("Student Exception throw form teacherController");
		
	}
	
}
