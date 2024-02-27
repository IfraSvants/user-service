package com.userservice.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.dto.UserDto;
import com.userservice.service.fasade.UserService;
import com.userservice.shared.ErrorMessage;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
public class UserController {
	
	private UserService userService;
	
	@GetMapping("")
	public ResponseEntity<List<UserDto>> findAll(){
			return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<?> save( @Valid @RequestBody() UserDto userDto){
		
		if ( userService.existsByEmail( userDto.getEmail() ) ) {
			
			return ResponseEntity
				.badRequest()
				.body(new ErrorMessage(
					"Error: Email is already in use!",
					new Date(),
					HttpStatus.BAD_REQUEST.value()
				));
			
		}
		UserDto saved = userService.save(userDto);
		
		return ResponseEntity.accepted().body(saved);
	}
	
	@PutMapping("/id/{id}")
	public ResponseEntity<UserDto> update(@Valid @RequestBody UserDto userDto, @PathVariable Integer id) throws NotFoundException {
		
		UserDto dto = userService.findById(id);
		userDto.setEmail(dto.getEmail());
		userDto.setPassword(dto.getPassword());
		
		System.out.println("passsssssssssssssssword :"+dto.getPassword());
		
		UserDto updated = userService.update(userDto, id);
		return ResponseEntity.accepted().body(updated);
		
	}
	
	@GetMapping("/id/{id}")
    public ResponseEntity<UserDto> findById( @PathVariable("id") Integer id) {
		UserDto UserDto = userService.findById(id);
    	return ResponseEntity.ok(UserDto);
    }
	
	@DeleteMapping("/id/{id}")
	public ResponseEntity<?> delete( @PathVariable("id") Integer id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
