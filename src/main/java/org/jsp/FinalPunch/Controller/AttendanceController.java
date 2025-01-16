package org.jsp.FinalPunch.Controller;

import org.jsp.FinalPunch.Dto.ApiResponse;
import org.jsp.FinalPunch.Entity.Attendance;
import org.jsp.FinalPunch.Exceptions.AttendanceAlreadyRegisteredException;
import org.jsp.FinalPunch.Service.AttendanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Component
@RestController
@RequestMapping("/Attendance")
public class AttendanceController {

    private static final Logger logger = LoggerFactory.getLogger(AttendanceController.class);

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Attendance>>> findAllAttendance() {
        try {
            logger.info("Entering findAllAttendance method");
            List<Attendance> allAttendance = attendanceService.findallEmployeeServiceAttendance();
            String description = allAttendance.isEmpty() ? "No Attendance Records Found" : "Attendance Records Fetched Successfully";
            ApiResponse<List<Attendance>> apiResponse = new ApiResponse<>(allAttendance);
            logger.debug("Exiting findAllAttendance method with response status: {}", HttpStatus.OK);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred in findAllAttendance: {}", e.getMessage(), e);
            ApiResponse<List<Attendance>> apiResponse = new ApiResponse<>(null, "Error occurred while fetching attendance records");
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Fetch attendance record by ID
    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<Optional<Attendance>>> findAttendanceById(@PathVariable int id) {
        try {
            logger.info("Fetching attendance for ID: {}", id);
            Optional<Attendance> attendance = attendanceService.findEmployeeServiceAttendanceById(id);
            String description = attendance.isEmpty() ? "No Attendance Record Found for Given ID" : "Attendance Record Fetched Successfully";
            ApiResponse<Optional<Attendance>> apiResponse = new ApiResponse<>(attendance);
            logger.debug("Exiting findAttendanceById method with response status: {}", HttpStatus.OK);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred in findAttendanceById: {}", e.getMessage(), e);
            ApiResponse<Optional<Attendance>> apiResponse = new ApiResponse<>(Optional.empty(), "Error occurred while fetching attendance record");
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Save a new attendance record
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Attendance>> saveAttendance(@RequestBody Attendance attendance) {
        try {
            logger.info("Attempting to save attendance: {}", attendance);
            Attendance savedAttendance = attendanceService.saveEmployeeServiceAttendance(attendance);
            String description = savedAttendance != null ? "Attendance Record Created Successfully" : "Failed to Create Attendance Record";
            ApiResponse<Attendance> apiResponse = new ApiResponse<>(savedAttendance, description);
            logger.debug("Exiting saveAttendance method with status: {}", HttpStatus.CREATED);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        }catch (AttendanceAlreadyRegisteredException e) {
            logger.warn("Attendance already registered: {}", e.getMessage());
            ApiResponse<Attendance> apiResponse = new ApiResponse<>(null, e.getMessage());
            return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            logger.error("Error occurred in saveAttendance: {}", e.getMessage(), e);
            ApiResponse<Attendance> apiResponse = new ApiResponse<>(null, "Error occurred while saving attendance record");
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update an attendance record by ID
    @PutMapping("/update/{id}/")
    public ResponseEntity<ApiResponse<Optional<Attendance>>> updateAttendanceById(@PathVariable int id, @RequestBody Attendance updateAttendanceDetails) {
        try {
            logger.debug("Attempting to update attendance for ID: {} with details: {}", id, updateAttendanceDetails);
            Optional<Attendance> updatedAttendance = attendanceService.updateEmployeeServiceAttendanceId(id, updateAttendanceDetails);
            String description = updatedAttendance.isEmpty() ? "No Attendance Record Found for Given ID" : "Attendance Record Updated Successfully";
            HttpStatus status = updatedAttendance.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
            ApiResponse<Optional<Attendance>> apiResponse = new ApiResponse<>(updatedAttendance, description);
            logger.debug("Exiting updateAttendanceById method with status: {}", status);
            return new ResponseEntity<>(apiResponse, status);
        } catch (Exception e) {
            logger.error("Error occurred in updateAttendanceById: {}", e.getMessage(), e);
            ApiResponse<Optional<Attendance>> apiResponse = new ApiResponse<>(Optional.empty(), "Error occurred while updating attendance record");
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete an attendance record by ID
    @DeleteMapping("/delete/{id}/")
    public ResponseEntity<ApiResponse<Optional<Attendance>>> deleteAttendanceById(@PathVariable int id) {
        try {
            logger.debug("Attempting to delete attendance for ID: {}", id);
            Optional<Attendance> deletedAttendance = attendanceService.deleteEmployeeServiceAttendanceId(id);
            String description = deletedAttendance.isEmpty() ? "No Attendance Record Found for Given ID" : "Attendance Record Deleted Successfully";
            HttpStatus status = deletedAttendance.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
            ApiResponse<Optional<Attendance>> apiResponse = new ApiResponse<>(deletedAttendance, description);
            logger.debug("Exiting deleteAttendanceById method with status: {}", status);
            return new ResponseEntity<>(apiResponse, status);
        } catch (Exception e) {
            logger.error("Error occurred in deleteAttendanceById: {}", e.getMessage(), e);
            ApiResponse<Optional<Attendance>> apiResponse = new ApiResponse<>(Optional.empty(), "Error occurred while deleting attendance record");
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
