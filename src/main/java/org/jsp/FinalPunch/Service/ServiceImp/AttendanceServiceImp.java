package org.jsp.FinalPunch.Service.ServiceImp;

import org.jsp.FinalPunch.Entity.Attendance;
import org.jsp.FinalPunch.Entity.Employee;
import org.jsp.FinalPunch.Entity.Leaves;
import org.jsp.FinalPunch.Enum.PresentStatus;
import org.jsp.FinalPunch.Exceptions.AttendanceAlreadyRegisteredException;
import org.jsp.FinalPunch.Exceptions.EmployeeNotRegisteredException;
import org.jsp.FinalPunch.Repository.AttendanceRepository;
import org.jsp.FinalPunch.Repository.EmployeeRepository;
import org.jsp.FinalPunch.Repository.LeavesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class AttendanceServiceImp {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private LeavesRepository leavesRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private LeavesServiceImpl leavesService;



    public List<Attendance> findallEmployeeDaoAttendance() {
        return attendanceRepository.findAll();
    }


    public Attendance saveEmployeeDaoAttendance(Attendance attendance) {
        Optional<Employee> employee=employeeRepository.findById(attendance.getEmployee().getId());

        if (employee.isPresent()) {
            Employee employee1 = employee.get();

            if (attendance.getEmployeeName() == null) {
                attendance.setEmployeeName(employee1.getEmployeeName());
            }


             //Get the last recorded attendance for this employee
            Optional<Attendance> existingAttendanceOpt = attendanceRepository.findTopByEmployeeAndDateOrderByIdDesc(employee1, attendance.getDate());

            if (existingAttendanceOpt.isPresent()) {
                throw new AttendanceAlreadyRegisteredException("Attendance already registered for date: " + attendance.getDate());
            }
            Attendance lastAttendance = attendanceRepository.findTopByEmployeeOrderByIdDesc(employee1)
                    .orElse(null);

            // Calculate attendance count based on last attendance and current status
            if (lastAttendance != null) {
                // Update total attendance based on the previous record
                attendance.setTotalPresent(lastAttendance.getTotalPresent());
                attendance.setTotalAbsent(lastAttendance.getTotalAbsent());
                attendance.setTotalLeaves(lastAttendance.getTotalLeaves());

                // Update the counts based on the current attendance status
                updateAttendanceCounts(attendance);
            } else {
                // If this is the first attendance, initialize counts
                initializeFirstAttendance(attendance);
                updateAttendanceCounts(attendance);
            }

            // Save the attendance record
            Attendance savedAttendance = attendanceRepository.save(attendance);

            updateEmployeeLeaves(employee1, attendance);

            return savedAttendance;

            //return attendanceRepository.save(attendance);
        } else {
            throw new RuntimeException("Employee not found with ID: " + attendance.getEmployee().getId());
        }
    }

   //  Get the most recent attendance record for the employee
    private Attendance getLastAttendanceForEmployee(Employee employee , Date date) {
        return attendanceRepository.findTopByEmployeeAndDateOrderByIdDesc(employee , date).orElse(null);
    }

    // Update the attendance counts based on the current record
    private void updateAttendanceCounts(Attendance attendance) {
        // Update the counts based on the present status
        if (attendance.getPresentStatus() != null) {
            switch (attendance.getPresentStatus()) {
                case PRESENT:
                    attendance.setTotalPresent(attendance.getTotalPresent() + 1);
                    break;
                case ABSENT:
                    attendance.setTotalAbsent(attendance.getTotalAbsent() + 1);
                    break;
                case CASUALLEAVE:
                    attendance.setTotalLeaves(attendance.getTotalLeaves() + 1);
                    break;
                case EARNEDLEAVE:
                    attendance.setTotalLeaves(attendance.getTotalLeaves() + 1);
                    break;
                default:
                    // If no present status is set, assume absent
                    attendance.setTotalAbsent(attendance.getTotalAbsent() + 1);
                    break;
            }
        } else {
            // If no status is set, assume absent by default
            attendance.setTotalAbsent(attendance.getTotalAbsent() + 1);
        }
    }

    // Initialize counts for the first attendance record
    private void initializeFirstAttendance(Attendance attendance) {
        attendance.setTotalPresent(0);  // First attendance record, no prior attendance
        attendance.setTotalAbsent(0);
        attendance.setTotalLeaves(0);
    }

    private void updateEmployeeLeaves(Employee employee, Attendance attendance) {
        Optional<Leaves> leavesOpt = leavesRepository.findById(employee.getId());
        Optional<Employee> employeeOpt = employeeRepository.findById(employee.getId());

        if (employeeOpt.isPresent()) {
            Employee employeeFromDb = employeeOpt.get();
            Leaves leaves;

            if (leavesOpt.isPresent()) {
                leaves = leavesOpt.get();

                // Update the leaves based on attendance status
                switch (attendance.getPresentStatus()) {
                    case CASUALLEAVE:
                        leaves.setCasualLeaves(leaves.getCasualLeaves() + 1);
                        break;
                    case EARNEDLEAVE:
                        leaves.setEarnedLeaves(leaves.getEarnedLeaves() + 1);
                        break;
                    default:
                        break;
                }

                // Update the leaves summary
                leaves.setId(attendance.getEmployee().getId());
                leaves.setTotalLeaves(120);
                leaves.setTotalLeavesKept(leaves.getCasualLeaves() + leaves.getEarnedLeaves());
                leaves.setTotalLeavesRemaining(leaves.getTotalLeaves() - leaves.getTotalLeavesKept());

                // Save the updated leaves record
                leavesRepository.save(leaves);

            } else {
                // If no leaves record exists, create a new one
                Leaves newLeaves = new Leaves();
                newLeaves.setEmployee(employee);
                //newLeaves.setId(attendance.getEmployee().getId());
                newLeaves.setEmployeeName(employee.getEmployeeName());
                newLeaves.setCasualLeaves(attendance.getPresentStatus() == PresentStatus.CASUALLEAVE ? 1 : 0);
                newLeaves.setEarnedLeaves(attendance.getPresentStatus() == PresentStatus.EARNEDLEAVE ? 1 : 0);
                newLeaves.setTotalLeaves(120);  // Assume total leaves are set for employee
                newLeaves.setTotalLeavesKept(newLeaves.getCasualLeaves() + newLeaves.getEarnedLeaves());
                newLeaves.setTotalLeavesRemaining(newLeaves.getTotalLeaves() - newLeaves.getTotalLeavesKept());

                // Save the new leaves record
                try {
                    leavesService.saveEmployeeLeaves1(newLeaves);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } else {
            throw new EmployeeNotRegisteredException("Employee not found with Id: " + employee.getId() + ". Please register.");
        }
    }


    public Optional<Attendance> findEmployeeDaoAttendanceBYId(int id) {
        Optional<Attendance> attendances=attendanceRepository.findById(id);
        return attendances;
    }

    public Optional<Attendance> deleteEmployeeDaoAttendanceById(int id) {
        Optional<Attendance> attendances=attendanceRepository.findById(id);
        if(attendances.isPresent()){
            attendanceRepository.deleteById(id);
        }
        return attendances;
    }

    public Optional<Attendance> updateEmployeeDaoAttendance(int id, Attendance updateEmployeeAttendanceDetails) {
        Optional<Attendance> existingAttendance = attendanceRepository.findById(id);
        Optional<Employee> existingEmployee=employeeRepository.findById(updateEmployeeAttendanceDetails.getEmployee().getId());
        Employee employee=existingEmployee.get();

        if (existingAttendance.isPresent()) {
            Attendance attendance = existingAttendance.get();

            // Update fields only if provided in updateEmployeeAttendanceDetails
            if (updateEmployeeAttendanceDetails.getEmployee() != null)
                attendance.setEmployee(updateEmployeeAttendanceDetails.getEmployee());
            if (updateEmployeeAttendanceDetails.getEmployeeName()!= null)
                attendance.setEmployeeName(employee.getEmployeeName());
            if (updateEmployeeAttendanceDetails.getDate() != null)
                attendance.setDate(updateEmployeeAttendanceDetails.getDate());
            if (updateEmployeeAttendanceDetails.getPresentStatus() != null)
                attendance.setPresentStatus(updateEmployeeAttendanceDetails.getPresentStatus());
            if (updateEmployeeAttendanceDetails.getTotalPresent() > 0)
                attendance.setTotalPresent(updateEmployeeAttendanceDetails.getTotalPresent());
            if (updateEmployeeAttendanceDetails.getTotalAbsent() > 0)
                attendance.setTotalAbsent(updateEmployeeAttendanceDetails.getTotalAbsent());
            if (updateEmployeeAttendanceDetails.getTotalLeaves() > 0)
                attendance.setTotalLeaves(updateEmployeeAttendanceDetails.getTotalLeaves());

            // Save updated entity
            Attendance updatedAttendance = attendanceRepository.save(attendance);
            return Optional.of(updatedAttendance);
        }

        return Optional.empty();
    }


}
