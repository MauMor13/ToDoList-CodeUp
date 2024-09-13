package com.mindhub.ToDoList.DTOs;
import com.mindhub.ToDoList.models.TaskStatus;

public record NewTaskDTO(long id, String title, String description, TaskStatus status) { }
