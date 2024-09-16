package com.mindhub.ToDoList.services.Impl;

import com.mindhub.ToDoList.DTOs.NewTaskDTO;
import com.mindhub.ToDoList.DTOs.TaskDTO;
import com.mindhub.ToDoList.models.Task;
import com.mindhub.ToDoList.models.TaskStatus;
import com.mindhub.ToDoList.models.UserEntity;
import com.mindhub.ToDoList.repositories.RepositoryTask;
import com.mindhub.ToDoList.repositories.RepositoryUser;
import com.mindhub.ToDoList.services.ServiceTask;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceTaskImpl implements ServiceTask {

    private final RepositoryTask repositoryTask;
    private final RepositoryUser repositoryUser;

    public ServiceTaskImpl(RepositoryTask repositoryTask,
                           RepositoryUser repositoryUser) {
        this.repositoryTask = repositoryTask;
        this.repositoryUser = repositoryUser;
    }

    @Override
    public ResponseEntity<Object> newTask(NewTaskDTO task) {

        Optional<UserEntity> user = repositoryUser.findById(task.userId());

        if (user.isEmpty() || !user.get().isActive())
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        if (task.title().isEmpty())
            return new ResponseEntity<>("Title was not entered", HttpStatus.PARTIAL_CONTENT);
        if (task.description().isEmpty())
            return new ResponseEntity<>("Description was not entered", HttpStatus.PARTIAL_CONTENT);

        Task newTask = new Task(task.title(), task.description());
        user.get().addTask(newTask);

        repositoryTask.save(newTask);
        repositoryUser.save(user.get());

        return  new ResponseEntity<>("new task created", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Object> getTask(long id) {

        Optional<Task> task = repositoryTask.findById(id);

        if (task.isPresent() && task.get().isActive())
            return new ResponseEntity<>(new TaskDTO(task.get()), HttpStatus.OK);

        return new ResponseEntity<>("Task not found", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> getTasks() {

        List <TaskDTO> taskDTOS = repositoryTask.findAll().stream().filter(Task::isActive).map(TaskDTO::new).toList();

        if (taskDTOS.isEmpty())
            return new ResponseEntity<>("no item found", HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(taskDTOS, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> patchTask(long id, String title, String description, TaskStatus status) {

        Optional<Task> task = repositoryTask.findById(id);
        String message = "Changes: \n";

        if (task.isEmpty() || !task.get().isActive())
            return new ResponseEntity<>("Task not found", HttpStatus.NOT_FOUND);

        if (!title.isEmpty()){
            task.get().setTitle(title);
            message = message.concat("* Successful title \n");
        }

        if (!description.isEmpty()){
            task.get().setDescription(description);
            message = message.concat("* Successful description \n");
        }

        if (status != null){
            task.get().setStatus(status);
            message = message.concat("* Successful status \n");
        }

        repositoryTask.save(task.get());

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteTask(long id) {

        Optional<Task> task = repositoryTask.findById(id);

        if(task.isEmpty() || !task.get().isActive())
            return new ResponseEntity<>("Task not found", HttpStatus.NOT_FOUND);

        task.get().setActive(false);

        repositoryTask.save(task.get());

        return new ResponseEntity<>("Delete successful the task", HttpStatus.OK);
    }


}
