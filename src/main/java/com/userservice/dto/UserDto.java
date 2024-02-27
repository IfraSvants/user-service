package com.userservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

	Integer userId;
	
	String email;
	
	@JsonIgnore
	String password;
	
	@NotNull(message="The name is required !")
	String name;

	@NotNull(message="The last name is required !")
	String lastName;

	@NotNull(message="The country is required !")
	String country;
	
}
