package com.sid.dao;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sid.entity.AppRole;



public interface RoleRepository extends JpaRepository<AppRole,Long>{
	public AppRole findByRoleName(String role);

}