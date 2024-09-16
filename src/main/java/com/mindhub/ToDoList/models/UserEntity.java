package com.mindhub.ToDoList.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean active = true;

    private String userName;

    private String password;

    private String email;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.EAGER)
    private Set<Task> tasks = new HashSet<>();

    public UserEntity(String userName, String password, String email){
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public void addTask(Task task){
        task.setUserEntity(this);
        this.tasks.add(task);
    }
}
