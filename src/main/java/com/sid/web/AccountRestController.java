package com.sid.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sid.entity.AppUser;
import com.sid.service.AccountService;

@RestController
public class AccountRestController {
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/register")
public AppUser register(@RequestBody RegisterForm userForm) {
		
		if(!userForm.getPassword().equals(userForm.getRepassword())) throw new RuntimeException("you must confirm  password");
		/*si utlisateur exist deja dans la base de donnees*/
		AppUser user=accountService.findUserByUsername(userForm.getUsername());
		if(user!=null) throw new RuntimeException("this user already exist");
		
		/* facon 1 de enrgiser par set ensuit get*/
		AppUser appuser=new AppUser();
		appuser.setUsername(userForm.getPassword());
		appuser.setPassword(userForm.getPassword());
		
		/* enrgistrer dictect dans la class appUser */
		/*AppUser user=new AppUser(null,userForm.getUsername(),userForm.getPassword(),null);*/
		return accountService.save(appuser);}
}
