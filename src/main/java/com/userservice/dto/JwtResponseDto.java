package com.userservice.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtResponseDto {
	
	String token;
	
	String type = "Bearer";
	
	Integer userId;
	
	String email;
	
	String message;
	
	public JwtResponseDto( String token , Integer userId , String email , String message ) {
		super();
		this.token = token;
		this.userId = userId;
		this.email = email;
		this.message= message;
	}

}
