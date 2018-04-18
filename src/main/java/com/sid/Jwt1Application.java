package com.sid;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sid.dao.TaskRepository;
import com.sid.entity.AppRole;
import com.sid.entity.AppUser;
import com.sid.entity.Task;
import com.sid.service.AccountService;

@SpringBootApplication
public class Jwt1Application implements CommandLineRunner {

	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private AccountService accountService;
	public static void main(String[] args) {
		SpringApplication.run(Jwt1Application.class, args);
	}

	@Bean
	public BCryptPasswordEncoder getBCPE() {return new BCryptPasswordEncoder();}


		@Override
		public void run(String... args) throws Exception {
			/*ajouter users*/
			accountService.save(new AppUser(null,"admin","123",null));
			accountService.save(new AppUser(null,"user","123",null));
			/*ajouter role*/
			accountService.saveRole(new AppRole(null,"ADMIN"));
			accountService.saveRole(new AppRole(null,"USER"));
			/*ajouter role users*/
			accountService.addRoleToUser("admin","ADMIN");
			accountService.addRoleToUser("user","User");
			
			Stream.of("T1","T2","T3").forEach(t->{taskRepository.save(new Task(null,t));
			});
			
			taskRepository.findAll().forEach(t->System.out.println(t.getTaskName()));
			
		}
}
