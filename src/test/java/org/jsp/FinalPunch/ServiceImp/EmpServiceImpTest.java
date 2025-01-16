package org.jsp.FinalPunch.ServiceImp;

import org.jsp.FinalPunch.Dto.ApiResponse;
import org.jsp.FinalPunch.Entity.Employee;
import org.jsp.FinalPunch.Enum.Role;
import org.jsp.FinalPunch.Repository.EmployeeRepository;
import org.jsp.FinalPunch.Service.ServiceImp.EmpServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmpServiceImpTest {

    @InjectMocks
    private EmpServiceImp empServiceImp;

    @Mock
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findallEmployeesDao() {
        List<Employee> employees = Arrays.asList(new Employee(), new Employee());
        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> result = empServiceImp.findallEmployeesDao();
        assertEquals(2, result.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void saveEmployeeDaoLyer() {
        Employee employee = new Employee();
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee result = empServiceImp.saveEmployeeDaoLyer(employee);
        assertNotNull(result);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void findEmployeeByDaoLayer() {
        Employee employee = new Employee();
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        Optional<Employee> result = empServiceImp.findEmployeeByDaoLayer(1);
        assertTrue(result.isPresent());
        verify(employeeRepository, times(1)).findById(1);
    }

    @Test
    void findEmployeeByDaoLayer_ShouldReturnEmptyWhenNotFound() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Employee> result = empServiceImp.findEmployeeByDaoLayer(1);
        assertFalse(result.isPresent());
        verify(employeeRepository, times(1)).findById(1);
    }


    @Test
    void deleteEmployeeByIdDaoLayer_ShouldDeleteWhenEmployeeExists() {
        Employee employee = new Employee();
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        Optional<Employee> result = empServiceImp.deleteEmployeeByIdDaoLayer(1);
        assertTrue(result.isPresent());
        verify(employeeRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteEmployeeByIdDaoLayer_ShouldReturnEmptyWhenNotFound() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Employee> result = empServiceImp.deleteEmployeeByIdDaoLayer(1);
        assertFalse(result.isPresent());
        verify(employeeRepository, times(0)).deleteById(1);
    }

    @Test
    void updateEmployeeDaoLayer_ShouldUpdateWhenEmployeeExists() {
        Employee existingEmployee = new Employee();
        Employee updatedDetails = new Employee();
        updatedDetails.setEmployeeName("Updated Name");
        when(employeeRepository.findById(1)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(existingEmployee)).thenReturn(existingEmployee);

        Optional<Employee> result = empServiceImp.updateEmployeeDaoLayer(1, updatedDetails);
        assertTrue(result.isPresent());
        assertEquals("Updated Name", existingEmployee.getEmployeeName());
        verify(employeeRepository, times(1)).save(existingEmployee);
    }

    @Test
    void updateEmployeeDaoLayer_ShouldReturnEmptyWhenNotFound() {
        Employee updatedDetails = new Employee();
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Employee> result = empServiceImp.updateEmployeeDaoLayer(1, updatedDetails);
        assertFalse(result.isPresent());
        verify(employeeRepository, times(0)).save(any(Employee.class));
    }



}