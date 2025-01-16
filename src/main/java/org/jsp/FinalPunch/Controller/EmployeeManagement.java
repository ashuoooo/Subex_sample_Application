package org.jsp.FinalPunch.Controller;

import org.jsp.FinalPunch.Dto.ApiResponse;
import org.jsp.FinalPunch.Entity.Employee;
import org.jsp.FinalPunch.Enum.Role;
import org.jsp.FinalPunch.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Component
@RestController
@RequestMapping("/Employee")
public class EmployeeManagement {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Employee>>> findAllEmployees() {
        try {
            List<Employee> allEmployees = employeeService.findallEmployeeDetailsService();
            String description = allEmployees.isEmpty() ? "No Employees Found in Database" : "Employees are successfully fetched";
            ApiResponse<List<Employee>> apiResponse = new ApiResponse<>(allEmployees, description);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<Employee>> apiResponse = new ApiResponse<>(null, "An error occurred while fetching all employees");
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<ApiResponse<List<Employee>>> getEmployeeByRole(@PathVariable Role role) {
        try {
            ResponseEntity<ApiResponse<List<Employee>>> response = employeeService.getEmployeeByRole(role);
            return response;
        } catch (Exception e) {
            ApiResponse<List<Employee>> apiResponse = new ApiResponse<>(null, "An error occurred while fetching employees by role");
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/id/{id}/")
    public ResponseEntity<ApiResponse<Optional<Employee>>> findByIdEmployee(@PathVariable int id) {
        try {
            Optional<Employee> findedEmployee = employeeService.findEmployeeByIdService(id);
            String description = findedEmployee.isEmpty() ? "No Employees Found in Database" : "Employee is successfully fetched";
            ApiResponse<Optional<Employee>> apiResponse = new ApiResponse<>(findedEmployee, description);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<Optional<Employee>> apiResponse = new ApiResponse<>(null, "An error occurred while fetching the employee by ID");
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Employee>> saveEmployee(@RequestBody Employee employee) {
        try {
            Employee savedEmployee = employeeService.saveEmployeeService(employee);
            ApiResponse<Employee> apiResponse = new ApiResponse<>(savedEmployee);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            String errorMessage = "Email already exists. Please provide a unique email address.";
            ApiResponse<Employee> apiResponse = new ApiResponse<>(errorMessage);
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<Employee> apiResponse = new ApiResponse<>("An unexpected error occurred. Please try again.");
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}/")
    public ResponseEntity<ApiResponse<Optional<Employee>>> updatebyId(@PathVariable int id, @RequestBody Employee updateEmployeeDetails) {
        try {
            Optional<Employee> updateEmployee = employeeService.updateEmployeeIdService(id, updateEmployeeDetails);
            String description = updateEmployee.isEmpty() ? "No Employee is Found" : "Employee is Found and Updated Successfully";
            HttpStatus status = updateEmployee.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
            ApiResponse<Optional<Employee>> apiResponse = new ApiResponse<>(updateEmployee, description);
            return new ResponseEntity<>(apiResponse, status);
        } catch (Exception e) {
            ApiResponse<Optional<Employee>> apiResponse = new ApiResponse<>(null, "An error occurred while updating the employee");
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}/")
    public ResponseEntity<ApiResponse<Optional<Employee>>> deleteEmployee(@PathVariable int id) {
        try {
            Optional<Employee> deleteEmployee = employeeService.deleteEmployeeIdService(id);
            String description = deleteEmployee.isEmpty() ? "Employee Not Found" : "Employee deleted successfully";
            HttpStatus status = deleteEmployee.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
            ApiResponse<Optional<Employee>> apiResponse = new ApiResponse<>(deleteEmployee, description);
            return new ResponseEntity<>(apiResponse, status);
        } catch (Exception e) {
            ApiResponse<Optional<Employee>> apiResponse = new ApiResponse<>(null, "An error occurred while deleting the employee");
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
