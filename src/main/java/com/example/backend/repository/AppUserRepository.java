package com.example.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.model.AppUser;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser,Long>{
	
	public AppUser findByUserId(Long id);
	public AppUser findByUserEmail(String userEmail);
}
