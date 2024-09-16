package com.mindhub.ToDoList.services;

import com.mindhub.ToDoList.DTOs.NewTaskDTO;
import com.mindhub.ToDoList.models.TaskStatus;
import org.springframework.http.ResponseEntity;

public interface ServiceTask {
    ResponseEntity<Object> newTask(NewTaskDTO newTaskDTO);

    ResponseEntity<Object> getTask(long id);

    ResponseEntity<Object> getTasks();

    ResponseEntity<Object> patchTask(long id, String title, String description, TaskStatus status);

    ResponseEntity<Object> deleteTask(long id);
}
