package com.mindhub.ToDoList.controllers;

import com.mindhub.ToDoList.DTOs.NewTaskDTO;
import com.mindhub.ToDoList.models.TaskStatus;
import com.mindhub.ToDoList.services.ServiceTask;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final ServiceTask serviceTask;

    public TaskController(ServiceTask serviceTask) {
        this.serviceTask = serviceTask;
    }

    @PostMapping("/new_task")
    public ResponseEntity<Object> newTask (@RequestBody NewTaskDTO newTaskDTO){
        return serviceTask.newTask(newTaskDTO);
    }

    @GetMapping("/get_task")
    public ResponseEntity<Object> getTask (@RequestParam long id){
        return serviceTask.getTask(id);
    }

    @GetMapping("get_tasks")
    public ResponseEntity<Object> getTasks (){
        return serviceTask.getTasks();
    }

    @PatchMapping("/patch_task")
    public ResponseEntity<Object> patchTask (@RequestParam(required = true) long id,
                                             @RequestParam(required = false) String title,
                                             @RequestParam(required = false) String description,
                                             @RequestParam(required = false) TaskStatus status){
        return serviceTask.patchTask(id, title, description, status);
    }

    @DeleteMapping("/delete_task")
    public ResponseEntity<Object> deleteTask (@RequestParam(required = true) long id){
        return serviceTask.deleteTask(id);
    }
}
