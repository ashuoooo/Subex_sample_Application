package org.jsp.FinalPunch.Controller;

import org.jsp.FinalPunch.Dto.ApiResponse;
import org.jsp.FinalPunch.Entity.TaskBox;
import org.jsp.FinalPunch.Service.TaskBoxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/taskbox")
public class TaskBoxController {

    @Autowired
    private TaskBoxService taskBoxService;

    private static final Logger logger = LoggerFactory.getLogger(TaskBoxController.class);

    // Fetch all task records
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<TaskBox>>> findAllTaskBoxes() {
        try {
            logger.debug("Fetching all task records");
            List<TaskBox> allTaskBoxes = taskBoxService.findallTaskBoxService();
            String description = allTaskBoxes.isEmpty() ? "No Task Records Found" : "Task Records Fetched Successfully";
            ApiResponse<List<TaskBox>> apiResponse = new ApiResponse<>(allTaskBoxes, description);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching task records", e);
            ApiResponse<List<TaskBox>> apiResponse = new ApiResponse<>("Internal server error while fetching task records");
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Fetch task record by ID
    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<Optional<TaskBox>>> findTaskBoxById(@PathVariable int id) {
        try {
            Optional<TaskBox> taskBox = taskBoxService.findEmployeeTaskById(id);
            String description = taskBox.isEmpty() ? "No Task Record Found for Given ID" : "Task Record Fetched Successfully";
            ApiResponse<Optional<TaskBox>> apiResponse = new ApiResponse<>(taskBox, description);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching task record with ID: " + id, e);
            ApiResponse<Optional<TaskBox>> apiResponse = new ApiResponse<>("Internal server error while fetching task record");
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Save a new task record
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<TaskBox>> saveTaskBox(@RequestBody TaskBox taskBox) {
        try {
            TaskBox savedTaskBox = taskBoxService.saveEmployeeTaskBox(taskBox);
            String description = savedTaskBox != null ? "Task Record Created Successfully" : "Failed to Create Task Record";
            ApiResponse<TaskBox> apiResponse = new ApiResponse<>(savedTaskBox, description);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error occurred while saving task record", e);
            ApiResponse<TaskBox> apiResponse = new ApiResponse<>("Internal server error while saving task record");
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update a task record by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Optional<TaskBox>>> updateTaskBoxById(@PathVariable int id, @RequestBody TaskBox updateTaskBoxDetails) {
        try {
            Optional<TaskBox> updatedTaskBox = taskBoxService.updateEmployeeTaskBoxId(id, updateTaskBoxDetails);
            String description = updatedTaskBox.isEmpty() ? "No Task Record Found for Given ID" : "Task Record Updated Successfully";
            HttpStatus status = updatedTaskBox.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
            ApiResponse<Optional<TaskBox>> apiResponse = new ApiResponse<>(updatedTaskBox, description);
            return new ResponseEntity<>(apiResponse, status);
        } catch (Exception e) {
            logger.error("Error occurred while updating task record with ID: " + id, e);
            ApiResponse<Optional<TaskBox>> apiResponse = new ApiResponse<>("Internal server error while updating task record");
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a task record by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Optional<TaskBox>>> deleteTaskBoxById(@PathVariable int id) {
        try {
            Optional<TaskBox> deletedTaskBox = taskBoxService.deleteEmployeeTaskId(id);
            String description = deletedTaskBox.isEmpty() ? "No Task Record Found for Given ID" : "Task Record Deleted Successfully";
            HttpStatus status = deletedTaskBox.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
            ApiResponse<Optional<TaskBox>> apiResponse = new ApiResponse<>(deletedTaskBox, description);
            return new ResponseEntity<>(apiResponse, status);
        } catch (Exception e) {
            logger.error("Error occurred while deleting task record with ID: " + id, e);
            ApiResponse<Optional<TaskBox>> apiResponse = new ApiResponse<>("Internal server error while deleting task record");
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to get tasks by employee name
    @GetMapping("/by-employee/{employeeName}")
    public ResponseEntity<ApiResponse<List<TaskBox>>> getTasksByEmployeeName(@PathVariable String employeeName) {
        try {
            List<TaskBox> tasks = taskBoxService.findTasksByEmployeeName(employeeName);
            ApiResponse<List<TaskBox>> response = new ApiResponse<>(tasks, "Tasks fetched successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<TaskBox>> response = new ApiResponse<>("Error fetching tasks by employee name.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to get tasks by status
    @GetMapping("/by-status/{status}")
    public ResponseEntity<ApiResponse<List<TaskBox>>> getTasksByStatus(@PathVariable String status) {
        try {
            List<TaskBox> tasks = taskBoxService.findTasksByStatus(status);
            ApiResponse<List<TaskBox>> response = new ApiResponse<>(tasks, "Tasks fetched successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<TaskBox>> response = new ApiResponse<>("Error fetching tasks by status.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to get tasks by task description
    @GetMapping("/by-description/{description}")
    public ResponseEntity<ApiResponse<List<TaskBox>>> getTasksByDescription(@PathVariable String description) {
        try {
            List<TaskBox> tasks = taskBoxService.findTasksByDescription(description);
            ApiResponse<List<TaskBox>> response = new ApiResponse<>(tasks, "Tasks fetched successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<TaskBox>> response = new ApiResponse<>("Error fetching tasks by description.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
