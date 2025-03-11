package com.victoryboard.main;

import com.victoryboard.main.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication(scanBasePackages = "com.victoryboard.main")
public class VictoryBoardApplication {
	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(VictoryBoardApplication.class, args);
	}
	@PostConstruct
	public void init(){
		try{
			System.out.println("creating super user.....");
			userService.createSuperUser("victoryBoard","1122aabb","postify@gmail.com");
			System.out.println("successfully created super user!!");
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("Error creating  super user: "+e.getMessage());
		}
	}

}
