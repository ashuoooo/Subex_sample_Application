package org.jsp.FinalPunch.Service;

import org.jsp.FinalPunch.Dto.ApiResponse;
import org.jsp.FinalPunch.Enum.Role;
import org.jsp.FinalPunch.Service.ServiceImp.EmpServiceImp;
import org.jsp.FinalPunch.Entity.Employee;
import org.jsp.FinalPunch.Exceptions.EmployeeNotRegisteredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
@Service
public class EmployeeService {

    @Autowired
    private EmpServiceImp empServiceImp;


    public List<Employee> findallEmployeeDetailsService() {
        return empServiceImp.findallEmployeesDao();
    }
    public Employee saveEmployeeService(Employee employee) {
        return empServiceImp.saveEmployeeDaoLyer(employee);
    }

    public Optional<Employee> findEmployeeByIdService(int id) {
        Optional<Employee> employeefinded=empServiceImp.findEmployeeByDaoLayer(id);
       if(employeefinded.isEmpty()){
           throw new EmployeeNotRegisteredException("Employee with Id  "+id+"  is not registered . Please Register");
       }
       return employeefinded;
    }

    public Optional<Employee> deleteEmployeeIdService(int id) {
        Optional<Employee> employeefinded= empServiceImp.deleteEmployeeByIdDaoLayer(id);
        if(employeefinded.isEmpty()){
            throw new EmployeeNotRegisteredException("Employee with Id  "+id+"  is not registered . Please Register");
        }
        return employeefinded;
    }

    public Optional<Employee> updateEmployeeIdService(int id, Employee updateEmployeeDetails) {
        Optional<Employee> employeefinded= empServiceImp.updateEmployeeDaoLayer(id , updateEmployeeDetails);
        if(employeefinded.isEmpty()){
            throw new EmployeeNotRegisteredException("Employee with Id  "+id+"  is not registered . Please Register");
        }
        return employeefinded;
    }


    public ResponseEntity<ApiResponse<List<Employee>>> getEmployeeByRole(Role role) {
        return empServiceImp.getEmployeeByRole(role);
    }
}
