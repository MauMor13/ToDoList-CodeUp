package com.mindhub.ToDoList;

import com.mindhub.ToDoList.models.Task;
import com.mindhub.ToDoList.models.UserEntity;
import com.mindhub.ToDoList.repositories.RepositoryTask;
import com.mindhub.ToDoList.repositories.RepositoryUser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ToDoListApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToDoListApplication.class, args);
	}

	@Bean
	public CommandLineRunner initDataBase (RepositoryTask repositoryTask,
										   RepositoryUser repositoryUser){
		return (args) -> {

			//new user object and task
			UserEntity userOne = new UserEntity("Mauri", "1313Mauricio", "mauri@gmail.com");
			Task taskOne = new Task("Shopping list", "Buy food for the dog");
			repositoryUser.save(userOne);

			//add task a user
			userOne.addTask(taskOne);

			//save in database
			repositoryTask.save(taskOne);
			repositoryUser.save(userOne);
		};
	}
}
