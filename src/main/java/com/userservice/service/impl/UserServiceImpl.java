package com.userservice.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.userservice.dao.UserDao;
import com.userservice.dto.UserDto;
import com.userservice.exception.EntityNotFoundException;
import com.userservice.models.UserEntity;
import com.userservice.service.fasade.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private UserDao userDao;
	private ModelMapper modelMapper;
	
	@Override
	public List<UserDto> findAll() {
		return userDao
				.findAll()
				.stream().map( el->modelMapper.map(el, UserDto.class) )
				.collect(Collectors.toList())
				;
	}

	@Override
	public UserDto save(UserDto userDto) {
		UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
		try {
			
			// Set the roles directly on the userEntity (to avoid detached entity issue)
			System.out.println(userEntity);
			
			UserEntity saved = userDao.save(userEntity);
			
			return modelMapper.map(saved, UserDto.class);
		} catch (Exception e) {
			// Log or handle the exception as needed
	        e.printStackTrace(); // or log.error("Error saving user", e);
	        // Optionally, you can throw a custom exception or return a specific error response.
	        return null; 
		}
	}

	@Override
	public UserDto update(UserDto userDto, Integer id) throws NotFoundException {
		
		Optional<UserEntity> userOptional = userDao.findById(id);
		
		if(userOptional.isPresent()) {
			
			UserEntity userEntity= modelMapper.map(userDto, UserEntity.class);
			userEntity.setUserId(id);

			UserEntity updated = userDao.save(userEntity);
			return modelMapper.map(updated, UserDto.class);
			
		}else {
			throw new EntityNotFoundException("User Not found");
		}
	}

	@Override
	public UserDto findById(Integer id) {
		
		UserEntity userEntity = userDao.findById(id).orElseThrow( ()->new EntityNotFoundException("User Not Found"));
		
		return modelMapper.map(userEntity , UserDto.class);
	}

	@Override
	public void delete(Integer id) {
		
		UserEntity userEntity = userDao.findById(id).orElseThrow( ()->new EntityNotFoundException("User Not Found"));
		userDao.delete(userEntity);
		
	}

	@Override
	public Boolean existsByEmail(String email) {
		return userDao.existsByEmail(email);
	}

}
