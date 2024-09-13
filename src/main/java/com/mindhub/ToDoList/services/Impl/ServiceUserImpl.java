package com.mindhub.ToDoList.services.Impl;

import com.mindhub.ToDoList.DTOs.NewUserDTO;
import com.mindhub.ToDoList.DTOs.UserDTO;
import com.mindhub.ToDoList.models.UserEntity;
import com.mindhub.ToDoList.repositories.RepositoryUser;
import com.mindhub.ToDoList.services.ServiceUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import static com.mindhub.ToDoList.utils.Utilities.isValidPassword;

@Service
public class ServiceUserImpl implements ServiceUser {

    private final RepositoryUser repositoryUser;

    public ServiceUserImpl(RepositoryUser repositoryUser) {
        this.repositoryUser = repositoryUser;
    }

    @Override
    public ResponseEntity<Object> newUser(NewUserDTO user) {

        if(user.userName().isEmpty())
            return new ResponseEntity<>("username was not entered", HttpStatus.PARTIAL_CONTENT);
        if (user.email().isEmpty())
            return new ResponseEntity<>("email was not entered", HttpStatus.PARTIAL_CONTENT);
        if (user.password().isEmpty())
            return new ResponseEntity<>("password was not entered", HttpStatus.PARTIAL_CONTENT);
        if (!isValidPassword(user.password()))
            return new ResponseEntity<>("Invalid password, include a capital letter, a number or special digit, it must be greater than six characters", HttpStatus.BAD_REQUEST);

        UserEntity newUser = new UserEntity( user.userName(), user.password(), user.email() );
        repositoryUser.save(newUser);
        return  new ResponseEntity<>("new user created", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Object> getUser(long id) {

        Optional<UserEntity> user = repositoryUser.findById(id);

        return user.<ResponseEntity<Object>>map(userEntity -> new ResponseEntity<>(userEntity, HttpStatus.OK))
                        .orElseGet(() -> new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND));

    }

    @Override
    public ResponseEntity<Object> getUsers() {

        List<UserDTO> userDTOS = repositoryUser.findAll().stream().map( UserDTO::new).toList();

        if (userDTOS.isEmpty())
            return new ResponseEntity<>("no item found", HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> patchUser(Long id, String email, String password, String userName) {

        Optional<UserEntity> user = repositoryUser.findById(id);

        if (user.isEmpty())
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>("", HttpStatus.OK);
    }

}
