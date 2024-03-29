package com.userservice.service.fasade;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.userservice.dto.UserDto;

public interface UserService {

	List<UserDto> findAll();
	
	UserDto save( UserDto userDto );
	
	UserDto update( UserDto userDto , Integer id ) throws NotFoundException;
	
	UserDto findById( Integer id );
	
	void delete( Integer id );
	
	Boolean existsByEmail(String email);
	
}
