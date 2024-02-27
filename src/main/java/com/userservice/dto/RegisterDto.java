package com.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterDto {

	@NotBlank
	@Size(max = 50)
	@Email
	@NotNull(message="The email is required !")
	String email;
	
	@NotBlank
	@Size(min = 6, max = 40)
	@NotNull(message="The password is required !")
	String password;
	
	@NotBlank
	@NotNull(message="The name is required !")
	String name;
	
	@NotBlank
	@NotNull(message="The last name is required !")
	String lastName;
	
	@NotNull(message="The country is required !")
	String country;
	
	
}
