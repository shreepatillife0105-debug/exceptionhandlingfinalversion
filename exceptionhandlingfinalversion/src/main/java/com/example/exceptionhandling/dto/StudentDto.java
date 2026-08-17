package com.example.exceptionhandling.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudentDto {
	
	 @NotBlank(message = "Name is required")
	    @Size(
	        min = 3,
	        max = 50,
	        message = "Name must be between 3 and 50 characters"
	    )
	    private String name;

	    @NotBlank(message = "Email is required")
	    @Email(message = "Invalid email format")
	    private String email;

	    @NotBlank(message = "Mobile number is required")
	    @Pattern(
	        regexp = "^[0-9]{10}$",
	        message = "Mobile number must contain exactly 10 digits"
	    )
	    private String mobile;

	    @Min(
	        value = 18,
	        message = "Age must be at least 18"
	    )
	    @Max(
	        value = 100,
	        message = "Age cannot be greater than 100"
	    )
	    private Integer age;

}
