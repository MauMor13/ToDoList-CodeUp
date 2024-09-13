package com.mindhub.ToDoList.repositories;

import com.mindhub.ToDoList.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryUser extends JpaRepository <UserEntity, Long> {

}
