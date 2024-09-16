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

        if (user.isPresent() && user.get().isActive())
            return new ResponseEntity<>(new UserDTO(user.get()), HttpStatus.OK);

        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> getUsers() {

        List<UserDTO> userDTOS = repositoryUser.findAll().stream().filter(UserEntity::isActive).map( UserDTO::new).toList();

        if (userDTOS.isEmpty())
            return new ResponseEntity<>("no item found", HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> patchUser(Long id, String email, String password, String userName) {

       Optional<UserEntity> user = repositoryUser.findById(id);
        String message = "Changes: \n";

        if (user.isEmpty() || !user.get().isActive())
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

        if (!email.isEmpty()){
            user.get().setEmail(email);
            message = message.concat("* Successful email \n");
        }

        if (!password.isEmpty()){
            if (isValidPassword(password)){
                user.get().setPassword(password);
                message = message.concat("* Successful password change \n");
            }
            else
                message = message.concat("* Invalid password, include a capital letter, a number or special digit, it must be greater than six characters \n");
        }

        if (!userName.isEmpty()){
            user.get().setUserName(userName);
            message = message.concat("* Successful user name change \n");
        }

        repositoryUser.save(user.get());

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteUser(Long id) {

        Optional<UserEntity> user = repositoryUser.findById(id);

        if (user.isEmpty() || !user.get().isActive())
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

        user.get().setActive(false);

        repositoryUser.save(user.get());

        return new ResponseEntity<>("Delete successful the user", HttpStatus.OK);
    }


}
