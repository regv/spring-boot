package com.sid.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sid.entity.AppUser;



public interface UserRepository extends JpaRepository<AppUser,Long>{
	public AppUser findByUsername(String username);

}