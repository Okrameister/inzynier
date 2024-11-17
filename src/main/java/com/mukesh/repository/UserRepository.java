package com.mukesh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mukesh.models.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Integer>{

	@Query("select u from AppUser u where u.email LIKE %:email%")
	public AppUser findByEmail(String email);
	
	@Query("select u from AppUser u where u.firstName LIKE %:query% OR u.lastName LIKE %:query% OR u.email LIKE %:query%")
	public List<AppUser> searchUser(@Param("query") String query);

}
