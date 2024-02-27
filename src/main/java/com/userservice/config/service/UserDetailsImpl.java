package com.userservice.config.service;

import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.userservice.models.UserEntity;

public class UserDetailsImpl implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;

	private String email;

	@JsonIgnore
	private String password;
	
	public UserDetailsImpl( Integer id, String email, String password ) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
	}
	
	// Méthode statique pour construire un UserDetailsImpl à partir d'un objet UserEntity
		public static UserDetailsImpl build(UserEntity user) {

		    return new UserDetailsImpl(
		        user.getUserId(), 
		        user.getEmail(),
		        user.getPassword()
		        );
		  }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Integer getId() {
		
	    return id;
	    
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	// Méthode pour comparer deux instances UserDetailsImpl par leur identifiant
	@Override
	public boolean equals(Object o) {
	    if (this == o)
	      return true;
	    if (o == null || getClass() != o.getClass())
	      return false;
	    UserDetailsImpl user = (UserDetailsImpl) o;
	    return Objects.equals(id, user.id);
	}

}
