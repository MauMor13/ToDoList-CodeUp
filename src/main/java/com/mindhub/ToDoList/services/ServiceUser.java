package com.mindhub.ToDoList.services;

import com.mindhub.ToDoList.DTOs.NewUserDTO;
import org.springframework.http.ResponseEntity;

public interface ServiceUser {

    ResponseEntity<Object> newUser (NewUserDTO user);

    ResponseEntity<Object> getUser(long id);

    ResponseEntity<Object> getUsers();

    ResponseEntity<Object> patchUser(Long id, String email, String password, String userName);

    ResponseEntity<Object> deleteUser(Long id);
}
