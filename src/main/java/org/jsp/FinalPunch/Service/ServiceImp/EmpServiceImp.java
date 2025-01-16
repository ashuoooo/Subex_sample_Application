package org.jsp.FinalPunch.Service.ServiceImp;

import org.jsp.FinalPunch.Dto.ApiResponse;
import org.jsp.FinalPunch.Entity.Employee;
import org.jsp.FinalPunch.Enum.Role;
import org.jsp.FinalPunch.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EmpServiceImp {

    @Autowired
    private EmployeeRepository employeeRepository;


    public List<Employee> findallEmployeesDao() {
        return employeeRepository.findAll();
    }

    public Employee saveEmployeeDaoLyer(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Optional<Employee> findEmployeeByDaoLayer(int id) {
        Optional<Employee> employeees = employeeRepository.findById(id);
        return employeees;
    }

    public Optional<Employee> deleteEmployeeByIdDaoLayer(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            employeeRepository.deleteById(id);
        }
        return employee;
    }

    public Optional<Employee> updateEmployeeDaoLayer(int id, Employee updateEmployeeDetails) {
        Optional<Employee> updateEmployee = employeeRepository.findById(id);
        if (updateEmployee.isPresent()) {

            Employee employee = updateEmployee.get();

            // Update fields only if provided in updateEmployeeDetails
            if (updateEmployeeDetails.getEmployeeName() != null)
                employee.setEmployeeName(updateEmployeeDetails.getEmployeeName());
            if (updateEmployeeDetails.getEmailId() != null)
                employee.setEmailId(updateEmployeeDetails.getEmailId());
            if (updateEmployeeDetails.getRole() != null)
                employee.setRole(updateEmployeeDetails.getRole());
            if (updateEmployeeDetails.getMobileNumber() != null)
                employee.setMobileNumber(updateEmployeeDetails.getMobileNumber());
            if (updateEmployeeDetails.getHeadOfDepartment() != null)
                employee.setHeadOfDepartment(updateEmployeeDetails.getHeadOfDepartment());
            if (updateEmployeeDetails.getDateOfJoining() != null)
                employee.setDateOfJoining(updateEmployeeDetails.getDateOfJoining());
            if (updateEmployeeDetails.getCompany() != null)
                employee.setCompany(updateEmployeeDetails.getCompany());
            if (updateEmployeeDetails.getBusinessUnit() != null)
                employee.setBusinessUnit(updateEmployeeDetails.getBusinessUnit());
            if (updateEmployeeDetails.getHrbp() != null)
                employee.setHrbp(updateEmployeeDetails.getHrbp());
            if (updateEmployeeDetails.getManager() != null)
                employee.setManager(updateEmployeeDetails.getManager());

            // Save updated entity
            Employee updatedEmployee = employeeRepository.save(employee);
            return Optional.of(updatedEmployee);
        }
        return Optional.empty();
    }


    public ResponseEntity<ApiResponse<List<Employee>>> getEmployeeByRole(Role role) {
        List<Employee> employees = employeeRepository.findEmployeesByRole(role);
        if (employees.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(//HttpStatus.NOT_FOUND, //null,
                     "PLease store employees with that role"));
        } else {
            return ResponseEntity.ok(new ApiResponse<>(//HttpStatus.OK,
                    employees,
                    "Employees found suceessfully"
            ));
        }
    }
}
