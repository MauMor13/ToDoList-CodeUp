package com.mindhub.ToDoList.controllers;

import com.mindhub.ToDoList.services.ServiceTask;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final ServiceTask serviceTask;

    public TaskController(ServiceTask serviceTask) {
        this.serviceTask = serviceTask;
    }


}
