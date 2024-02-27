package com.userservice.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.config.jwt.JwtUtils;
import com.userservice.config.service.UserDetailsImpl;
import com.userservice.dto.JwtResponseDto;
import com.userservice.dto.LoginDto;
import com.userservice.dto.RegisterDto;
import com.userservice.dto.UserDto;
import com.userservice.service.fasade.UserService;
import com.userservice.shared.ErrorMessage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
  	@Autowired
  	private PasswordEncoder encoder;
  	
    @Autowired
    private JwtUtils jwtUtils;
    
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> authenticateUser(@Valid @RequestBody LoginDto loginDto) {

    	System.out.println("test");
    	Authentication authentication = authenticationManager.authenticate(
    			new UsernamePasswordAuthenticationToken(
					loginDto.getEmail(), 
					loginDto.getPassword()
    			));
    	System.out.println("test2");
    	SecurityContextHolder.getContext().setAuthentication(authentication);
    	String jwt = jwtUtils.generateJwtToken(authentication);
      
    	UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
    	
    	return ResponseEntity.ok(new JwtResponseDto(jwt, 
           userDetails.getId(), 
           userDetails.getUsername(),  
           "success"
           ));
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDto registerDto) {
    	
    	
    	UserDto userDto = new UserDto(null, registerDto.getEmail(), registerDto.getPassword(), registerDto.getName(), registerDto.getLastName(), registerDto.getCountry());
    	
    	if ( userService.existsByEmail( registerDto.getEmail() ) ) {
	  
    		return ResponseEntity
    				.badRequest()
    				.body(new ErrorMessage(
    						"Error: Email is already in use!",
    						new Date(),
    						HttpStatus.BAD_REQUEST.value()
    					)); 
    		}

    	String password = registerDto.getPassword();
      
    	String hashedPassword = encoder.encode(password);
      
    	userDto.setPassword(hashedPassword);
      
    	UserDto saved = userService.save(userDto);
      
    	Authentication authentication = authenticationManager.authenticate(
    			new UsernamePasswordAuthenticationToken(
	        		saved.getEmail(), 
	        		password 
    	        ));

    	SecurityContextHolder.getContext().setAuthentication(authentication);
    	String jwt = jwtUtils.generateJwtToken(authentication);
   
    	return ResponseEntity.ok(new JwtResponseDto(jwt, 
              saved.getUserId(),
              saved.getEmail(),
              "success"
              ));
    }
    
    @GetMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request) {
        // Efface le contexte d'authentification de Spring Security
        SecurityContextHolder.clearContext();
        
        // Supprime le jeton JWT côté client (par exemple, un cookie)
        // Vous pouvez également envoyer une réponse appropriée indiquant que l'utilisateur est déconnecté avec succès
        return ResponseEntity.ok("User logged out successfully.");
    }
    

}
