package com.mindhub.ToDoList.services;

import com.mindhub.ToDoList.DTOs.NewUserDTO;
import org.springframework.http.ResponseEntity;

public interface ServiceUser {

    ResponseEntity<Object> newUser (NewUserDTO user);

    ResponseEntity<Object> getUser(long id);
}
