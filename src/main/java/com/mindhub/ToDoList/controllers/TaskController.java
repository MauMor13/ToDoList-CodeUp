package com.mindhub.ToDoList.controllers;

import com.mindhub.ToDoList.DTOs.NewTaskDTO;
import com.mindhub.ToDoList.DTOs.TaskDTO;
import com.mindhub.ToDoList.models.TaskStatus;
import com.mindhub.ToDoList.services.ServiceTask;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final ServiceTask serviceTask;

    public TaskController(ServiceTask serviceTask) {
        this.serviceTask = serviceTask;
    }

    @PostMapping("/new_task")
    @Operation(
            summary = "Create a new task",
            description = "This endpoint allows for the creation of a new task for a specified user. It accepts a JSON payload with the task details and returns the status of the operation.",
            requestBody = @RequestBody(
                    description = "JSON payload containing the task details",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = NewTaskDTO.class,
                                    example = "{ \"userId\": 123, \"title\": \"New Task Title\", \"description\": \"Detailed description of the task\" }"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "New task successfully created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "206",
                            description = "Partial content - title or description was not entered",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
                            content = @Content
                    )
            }
    )
    public ResponseEntity<Object> newTask (@RequestBody NewTaskDTO newTaskDTO){
        return serviceTask.newTask(newTaskDTO);
    }

    @GetMapping("/get_task")
    @Operation(
            summary = "Retrieve task details",
            description = "This endpoint retrieves the details of a task based on the provided task ID. It returns the task's information if the ID is valid and the task is active.",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "The unique identifier of the task",
                            required = true,
                            example = "456"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Task details retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TaskDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Task not found",
                            content = @Content
                    )
            }
    )
    public ResponseEntity<Object> getTask (@RequestParam long id){
        return serviceTask.getTask(id);
    }

    @GetMapping("get_tasks")
    @Operation(
            summary = "Retrieve all active tasks",
            description = "This endpoint retrieves a list of all active tasks in the system. It returns the task details if there are active tasks available.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of active tasks retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TaskDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "No active tasks found",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content
                    )
            }
    )
    public ResponseEntity<Object> getTasks (){
        return serviceTask.getTasks();
    }

    @PatchMapping("/patch_task")
    @Operation(
            summary = "Update task details",
            description = "This endpoint allows for partial updates to a task's details. You can update the task's title, description, and status by providing the respective parameters.",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "The unique identifier of the task",
                            required = true,
                            example = "456"
                    ),
                    @Parameter(
                            name = "title",
                            description = "The new title of the task",
                            required = false,
                            example = "Updated Task Title"
                    ),
                    @Parameter(
                            name = "description",
                            description = "The new description of the task",
                            required = false,
                            example = "Updated detailed description of the task"
                    ),
                    @Parameter(
                            name = "status",
                            description = "The new status of the task",
                            required = false,
                            schema = @Schema(implementation = TaskStatus.class)
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Task details updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Task not found",
                            content = @Content
                    )
            }
    )
    public ResponseEntity<Object> patchTask (@RequestParam(required = true) long id,
                                             @RequestParam(required = false) String title,
                                             @RequestParam(required = false) String description,
                                             @RequestParam(required = false) TaskStatus status){
        return serviceTask.patchTask(id, title, description, status);
    }

    @DeleteMapping("/delete_task")
    @Operation(
            summary = "Deactivate a task",
            description = "This endpoint deactivates a task by setting its active status to false. It requires the task's unique identifier (ID) and returns the status of the operation.",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "The unique identifier of the task to be deactivated",
                            required = true,
                            example = "456"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Task successfully deactivated",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Task not found",
                            content = @Content
                    )
            }
    )
    public ResponseEntity<Object> deleteTask (@RequestParam(required = true) long id){
        return serviceTask.deleteTask(id);
    }
}
