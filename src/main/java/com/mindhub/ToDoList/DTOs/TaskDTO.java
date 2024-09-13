package com.mindhub.ToDoList.DTOs;

import com.mindhub.ToDoList.models.Task;
import com.mindhub.ToDoList.models.TaskStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TaskDTO {

    private long id;

    private String title;

    private String description;

    private TaskStatus status;

    public TaskDTO (Task task){
        this.id = task.getId();
        this.title = task.getTitle();
        this.status = task.getStatus();
        this.description = task.getDescription();
    }
}
