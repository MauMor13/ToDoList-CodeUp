package com.mindhub.ToDoList.DTOs;

import com.mindhub.ToDoList.models.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class UserDTO {

    private long id;

    private String userName;

    private String email;

    private List<TaskDTO> tasks = new ArrayList<>();

    public UserDTO (UserEntity user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.userName = user.getUserName();
        this.tasks = user.getTasks().stream().map(TaskDTO::new).toList();
    }
}
