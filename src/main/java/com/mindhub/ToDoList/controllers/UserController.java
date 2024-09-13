package com.mindhub.ToDoList.controllers;

import com.mindhub.ToDoList.DTOs.NewUserDTO;
import com.mindhub.ToDoList.services.ServiceUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final ServiceUser serviceUser;

    public UserController(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }

    @PostMapping("/new_user")
    public ResponseEntity<Object> newUser (@RequestBody NewUserDTO user){
        return serviceUser.newUser(user);
    }

    @GetMapping("/get_user")
    public ResponseEntity<Object> getUser (@RequestParam long id){
        return serviceUser.getUser(id);
    }

}
