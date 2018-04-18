package com.sid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sid.dao.RoleRepository;
import com.sid.dao.UserRepository;
import com.sid.entity.AppRole;
import com.sid.entity.AppUser;
@Service
@Transactional
public class AccountServiceImp implements AccountService {
	@Autowired
private BCryptPasswordEncoder bCryptPasswordEncoder; 
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Override
	public AppUser save(AppUser user) {
		String hashPW=bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(hashPW);
		return userRepository.save(user);
	}

	@Override
	public AppRole saveRole(AppRole role) {
		// TODO Auto-generated method stub
		return roleRepository.save(role);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		AppRole role=roleRepository.findByRoleName(roleName);
		AppUser user=userRepository.findByUsername(username);
		user.getRoles().add(role);
	}

	@Override
	public AppUser findUserByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username);
	}

}
