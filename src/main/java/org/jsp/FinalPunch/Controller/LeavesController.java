package org.jsp.FinalPunch.Controller;

import org.jsp.FinalPunch.Dto.ApiResponse;
import org.jsp.FinalPunch.Entity.Leaves;
import org.jsp.FinalPunch.Service.LeavesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Component
@RequestMapping("/leaves")
public class LeavesController {
    @Autowired
    private LeavesService leavesService;

    // Fetch all leave records
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Leaves>>> findAllLeaves() {
        try {
            List<Leaves> allLeaves = leavesService.findallEmployeeLeaves();
            String description = allLeaves.isEmpty() ? "No Leaves Records Found" : "Leaves Records Fetched Successfully";
            ApiResponse<List<Leaves>> apiResponse = new ApiResponse<>(allLeaves);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<Leaves>> apiResponse = new ApiResponse<>(null, "Error fetching leaves: " + e.getMessage());
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Fetch leave record by ID
    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<Optional<Leaves>>> findLeavesById(@PathVariable int id) {
        try {
            Optional<Leaves> leaves = leavesService.findEmployeeLeavesById(id);
            String description = leaves.isEmpty() ? "No Leaves Record Found for Given ID" : "Leaves Record Fetched Successfully";
            ApiResponse<Optional<Leaves>> apiResponse = new ApiResponse<>(leaves);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<Optional<Leaves>> apiResponse = new ApiResponse<>(Optional.empty(), "Error fetching leave: " + e.getMessage());
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Save a new leave record
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Leaves>> saveEmployeeLeaves(@RequestBody Leaves leaves) {
        try {
            Leaves savedEmployeeLeaves = leavesService.saveEmployeeLeaves(leaves);
            String description = savedEmployeeLeaves != null ? "Leave Record Created Successfully" : "Failed to Create Leave Record";
            ApiResponse<Leaves> apiResponse = new ApiResponse<>(savedEmployeeLeaves);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse<Leaves> apiResponse = new ApiResponse<>(null, "Error saving leave: " + e.getMessage());
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update a leave record by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Optional<Leaves>>> updateLeavesById(@PathVariable int id, @RequestBody Leaves updateLeavesDetails) {
        try {
            Optional<Leaves> updatedLeaves = leavesService.updateEmployeeLeavesById(id, updateLeavesDetails);
            String description = updatedLeaves.isEmpty() ? "No Leaves Record Found for Given ID" : "Leave Record Updated Successfully";
            HttpStatus status = updatedLeaves.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
            ApiResponse<Optional<Leaves>> apiResponse = new ApiResponse<>(updatedLeaves);
            return new ResponseEntity<>(apiResponse, status);
        } catch (Exception e) {
            ApiResponse<Optional<Leaves>> apiResponse = new ApiResponse<>(Optional.empty(), "Error updating leave: " + e.getMessage());
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a leave record by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Optional<Leaves>>> deleteLeavesById(@PathVariable int id) {
        try {
            Optional<Leaves> deletedLeaves = leavesService.deleteEmployeeLeavesId(id);
            String description = deletedLeaves.isEmpty() ? "No Leaves Record Found for Given ID" : "Leave Record Deleted Successfully";
            HttpStatus status = deletedLeaves.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
            ApiResponse<Optional<Leaves>> apiResponse = new ApiResponse<>(deletedLeaves);
            return new ResponseEntity<>(apiResponse, status);
        } catch (Exception e) {
            ApiResponse<Optional<Leaves>> apiResponse = new ApiResponse<>(Optional.empty(), "Error deleting leave: " + e.getMessage());
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
