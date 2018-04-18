package com.sid.service;

import com.sid.entity.AppRole;
import com.sid.entity.AppUser;

public interface AccountService {
public AppUser save(AppUser user);
public AppRole saveRole(AppRole role);
public void addRoleToUser(String username,String roleName);
public AppUser findUserByUsername(String username);
}
