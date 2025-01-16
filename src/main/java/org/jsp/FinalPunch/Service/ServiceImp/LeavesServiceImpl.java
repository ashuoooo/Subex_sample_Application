package org.jsp.FinalPunch.Service.ServiceImp;

import org.jsp.FinalPunch.Entity.Employee;
import org.jsp.FinalPunch.Entity.Leaves;
import org.jsp.FinalPunch.Repository.EmployeeRepository;
import org.jsp.FinalPunch.Repository.LeavesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LeavesServiceImpl {

    @Autowired
    private LeavesRepository leavesRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    public List<Leaves> findallEmployeeDaoLeaves() {
        return leavesRepository.findAll();
    }

//    public boolean checkAttendanceWithinLast24Hours(int employeeId) {
//        return attendanceRepository.countAttendanceInLast24Hours(employeeId) > 0;
//    }


    public Leaves saveEmployeeLeaves(Leaves leaves) {
        Optional<Employee> employee=employeeRepository.findById(leaves.getEmployee().getId());

        if (employee.isPresent()) {
            Employee employee1 = employee.get();

            if (leaves.getEmployeeName() == null) {
                leaves.setEmployeeName(employee1.getEmployeeName());
            }

//            if (checkAttendanceWithinLast24Hours(attendance.getEmployee().getId())) {
//                // Throw an exception or handle the condition as needed
//                throw new RuntimeException("Attendance can only be recorded once every 24 hours.");
//            }
            return leavesRepository.save(leaves);
        } else {
            throw new RuntimeException("Employee not found with ID: " + leaves.getId());
        }
    }

    public void saveEmployeeLeaves1(Leaves leaves) {
        Optional<Employee> employee=employeeRepository.findById(leaves.getEmployee().getId());

        if (employee.isPresent()) {
            Employee employee1 = employee.get();

            if (leaves.getEmployeeName() == null) {
                leaves.setEmployeeName(employee1.getEmployeeName());
            }

//            if (checkAttendanceWithinLast24Hours(attendance.getEmployee().getId())) {
//                // Throw an exception or handle the condition as needed
//                throw new RuntimeException("Attendance can only be recorded once every 24 hours.");
//            }
            leavesRepository.save(leaves);
        } else {
            throw new RuntimeException("Employee not found with ID: " + leaves.getId());
        }
    }

    public Optional<Leaves> findEmployeeLeavesbYId(int id) {
        Optional<Leaves> leaves=leavesRepository.findById(id);
        return leaves;
    }

    public Optional<Leaves> deleteEmployeeLeavesById(int id) {
        Optional<Leaves> leaves=leavesRepository.findById(id);
        if(leaves.isPresent()){
            leavesRepository.deleteById(id);
        }
        return leaves;
    }

    public Optional<Leaves> updateEmployeeLeaves(int id, Leaves updateEmployeeLeavesDetails) {
        Optional<Leaves> existingLeaves = leavesRepository.findById(id);
        Optional<Employee> existingEmployee=employeeRepository.findById(updateEmployeeLeavesDetails.getEmployee().getId());
        Employee employee=existingEmployee.get();
        if (existingLeaves.isPresent()) {
            Leaves leaves = existingLeaves.get();

            // Update fields only if provided in updateEmployeeLeavesDetails
            if (updateEmployeeLeavesDetails.getEmployee() != null)
                leaves.setEmployee(updateEmployeeLeavesDetails.getEmployee());
            if (updateEmployeeLeavesDetails.getEmployeeName() != null)
                leaves.setEmployeeName(employee.getEmployeeName());
            if (updateEmployeeLeavesDetails.getCasualLeaves() > 0)
                leaves.setCasualLeaves(updateEmployeeLeavesDetails.getCasualLeaves());
            if (updateEmployeeLeavesDetails.getEarnedLeaves() > 0)
                leaves.setEarnedLeaves(updateEmployeeLeavesDetails.getEarnedLeaves());
             if (updateEmployeeLeavesDetails.getTotalLeaves() > 0)
                leaves.setTotalLeaves(updateEmployeeLeavesDetails.getTotalLeaves());
            if (updateEmployeeLeavesDetails.getTotalLeavesRemaining() > 0)
                leaves.setTotalLeavesRemaining(updateEmployeeLeavesDetails.getTotalLeavesRemaining());

            // Save updated entity
            Leaves updatedLeaves = leavesRepository.save(leaves);
            return Optional.of(updatedLeaves);
        }

        return Optional.empty();
    }


}
