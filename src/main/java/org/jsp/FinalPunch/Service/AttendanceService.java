package org.jsp.FinalPunch.Service;

import org.jsp.FinalPunch.Service.ServiceImp.AttendanceServiceImp;
import org.jsp.FinalPunch.Entity.Attendance;
import org.jsp.FinalPunch.Entity.Employee;
import org.jsp.FinalPunch.Exceptions.EmployeeNotRegisteredException;
import org.jsp.FinalPunch.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
@Service
public class AttendanceService {
    @Autowired
    private AttendanceServiceImp attendanceServiceImp;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Attendance> findallEmployeeServiceAttendance() {

        return attendanceServiceImp.findallEmployeeDaoAttendance();

    }

    public Attendance saveEmployeeServiceAttendance(Attendance attendance) {
        Optional<Employee> employeefinded= employeeRepository.findById(attendance.getEmployee().getId());
        if(employeefinded.isEmpty()){
            throw new EmployeeNotRegisteredException("Employee with Id  "+attendance.getEmployee().getId()+"  is not registered . Please Register");
        }
        return attendanceServiceImp.saveEmployeeDaoAttendance(attendance);
    }



    public Optional<Attendance> findEmployeeServiceAttendanceById(int id) {
        return attendanceServiceImp.findEmployeeDaoAttendanceBYId(id);
    }

    public Optional<Attendance> deleteEmployeeServiceAttendanceId(int id) {

        Optional<Attendance> attendanceFinded= attendanceServiceImp.findEmployeeDaoAttendanceBYId(id);
        if(attendanceFinded.isEmpty()){
            throw new EmployeeNotRegisteredException("Attendance  with Id  "+ id +"  is not registered . Please Update Attendance");
        }
        return attendanceFinded;
    }

    public Optional<Attendance> updateEmployeeServiceAttendanceId(int id, Attendance updateEmployeeAttendanceDetails) {

        Optional<Attendance> attendanceFinded= attendanceServiceImp.updateEmployeeDaoAttendance(id,updateEmployeeAttendanceDetails);
        if(attendanceFinded.isEmpty()){
            throw new EmployeeNotRegisteredException("Attendance  with Id  "+ id +"  is not registered . Please Register");
        }
        return attendanceFinded;
    }
}
