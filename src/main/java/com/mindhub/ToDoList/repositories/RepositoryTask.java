package com.mindhub.ToDoList.repositories;

import com.mindhub.ToDoList.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryTask extends JpaRepository <Task, Long> {

}
