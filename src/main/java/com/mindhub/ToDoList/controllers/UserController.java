package com.mindhub.ToDoList.controllers;

import com.mindhub.ToDoList.DTOs.NewUserDTO;
import com.mindhub.ToDoList.DTOs.UserDTO;
import com.mindhub.ToDoList.services.ServiceUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final ServiceUser serviceUser;

    public UserController(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }

    @PostMapping("/new_user")
    @Operation(
            summary = "Create a new user",
            description = "Endpoint for user creation",
            requestBody = @RequestBody(
                    description = "JSON payload containing the user's details",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = NewUserDTO.class,
                                    example = "{ \"username\": \"john_doe\", \"password\": \"securePass123\", \"email\": \"john.doe@example.com\" }"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "New user created",
                            content = { @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)) }),
                    @ApiResponse(
                            responseCode = "206",
                            description = "Partial content",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid password",
                            content = @Content)
            })
    public ResponseEntity<Object> newUser (@RequestBody NewUserDTO user){
        return serviceUser.newUser(user);
    }

    @GetMapping("/get_user")
    @Operation(
            summary = "Retrieve user details",
            description = "This endpoint retrieves the details of a user based on the provided user ID. It returns the user's information if the ID is valid.",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "The unique identifier of the user",
                            required = true,
                            example = "123"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User details retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
                            content = @Content
                    )
            }
    )
    public ResponseEntity<Object> getUser (@RequestParam long id){
        return serviceUser.getUser(id);
    }

    @GetMapping("/get_users")
    @Operation(
            summary = "Retrieve all active users",
            description = "This endpoint retrieves a list of all active users in the system. It returns the user details if there are active users available.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of active users retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "No active users found",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content
                    )
            }
    )
    public ResponseEntity<Object> getUsers (){
        return serviceUser.getUsers();
    }

    @PatchMapping("/patch_user")
    @Operation(
            summary = "Update user details",
            description = "This endpoint allows for partial updates to a user's details. You can update the user's email, password, and username by providing the respective parameters.",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "The unique identifier of the user",
                            required = true,
                            example = "123"
                    ),
                    @Parameter(
                            name = "email",
                            description = "The new email address of the user",
                            required = false,
                            example = "new.email@example.com"
                    ),
                    @Parameter(
                            name = "password",
                            description = "The new password for the user. It must include a capital letter, a number, or a special character, and be greater than six characters.",
                            required = false,
                            example = "NewPass123!"
                    ),
                    @Parameter(
                            name = "userName",
                            description = "The new username for the user",
                            required = false,
                            example = "new_username"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User details updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
                            content = @Content
                    )
            }
    )
    public ResponseEntity<Object> patchUser(@RequestParam(required = true) Long id,
                                            @RequestParam(required = false) String email,
                                            @RequestParam(required = false) String password,
                                            @RequestParam(required = false) String userName){
        return serviceUser.patchUser(id, email, password, userName);
    }

    @DeleteMapping("/delete_user")
    @Operation(
            summary = "Deactivate a user",
            description = "This endpoint deactivates a user by setting their active status to false. It requires the user's unique identifier (ID) and returns the status of the operation.",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "The unique identifier of the user to be deactivated",
                            required = true,
                            example = "123"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User successfully deactivated",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
                            content = @Content
                    )
            }
    )
    public ResponseEntity<Object> deleteUser (@RequestParam(required = true) Long id){
        return serviceUser.deleteUser(id);
    }

}
