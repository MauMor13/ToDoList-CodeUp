package com.mindhub.ToDoList.DTOs;
import com.mindhub.ToDoList.models.TaskStatus;

public record TaskDTO(long id, String title, String description, TaskStatus status) { }
