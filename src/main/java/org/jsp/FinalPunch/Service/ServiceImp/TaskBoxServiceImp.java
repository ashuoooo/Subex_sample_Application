package org.jsp.FinalPunch.Service.ServiceImp;

import org.jsp.FinalPunch.Entity.Employee;
import org.jsp.FinalPunch.Entity.TaskBox;
import org.jsp.FinalPunch.Repository.EmployeeRepository;
import org.jsp.FinalPunch.Repository.TaskBoxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskBoxServiceImp {

    @Autowired
    private TaskBoxRepository taskBoxRepository;


    @Autowired
    private EmployeeRepository employeeRepository;
    private JdbcTemplate jdbcTemplate;

    public List<TaskBox> findallEmployeeTasks() {
        return taskBoxRepository.findAll();
    }

//    public boolean checkAttendanceWithinLast24Hours(int employeeId) {
//        return attendanceRepository.countAttendanceInLast24Hours(employeeId) > 0;
//    }


    public TaskBox saveEmployeeTasks(TaskBox taskBox) {
        Optional<Employee> employee=employeeRepository.findById(taskBox.getEmployee().getId());

        if (employee.isPresent()) {
            Employee employee1 = employee.get();

            if (taskBox.getEmployeeName() == null) {
                taskBox.setEmployeeName(employee1.getEmployeeName());
            }

//            if (checkAttendanceWithinLast24Hours(attendance.getEmployee().getId())) {
//                // Throw an exception or handle the condition as needed
//                throw new RuntimeException("Attendance can only be recorded once every 24 hours.");
//            }
            if (taskBox.getStatus() == null) {
                taskBox.setStatus("Pending"); // Default status is "Pending"
            }
            return taskBoxRepository.save(taskBox);
        } else {
            throw new RuntimeException("Employee not found with ID: " + taskBox.getId());
        }
    }

    public Optional<TaskBox> findEmployeeTaskBoxbYId(int id) {
        Optional<TaskBox> taskBox=taskBoxRepository.findById(id);
        return taskBox;
    }

    public Optional<TaskBox> deleteEmployeeTaskById(int id) {
        Optional<TaskBox> taskBox=taskBoxRepository.findById(id);
        if(taskBox.isPresent()){
            taskBoxRepository.deleteById(id);
        }
        return taskBox;
    }
    public Optional<TaskBox> updateTaskBox(int id, TaskBox updateTaskBoxDetails) {
        Optional<TaskBox> existingTaskBox = taskBoxRepository.findById(id);
        Optional<Employee> existingEmployee=employeeRepository.findById(updateTaskBoxDetails.getEmployee().getId());
        Employee employee=existingEmployee.get();

        if (existingTaskBox.isPresent()) {
            TaskBox taskBox = existingTaskBox.get();

            // Update fields only if provided in updateTaskBoxDetails
            if (updateTaskBoxDetails.getEmployee() != null)
                taskBox.setEmployee(updateTaskBoxDetails.getEmployee());
            if (updateTaskBoxDetails.getEmployeeName() != null)
                taskBox.setEmployeeName(employee.getEmployeeName());
            if (updateTaskBoxDetails.getTaskName() != null)
                taskBox.setTaskName(updateTaskBoxDetails.getTaskName());
            if (updateTaskBoxDetails.getTaskDescription() != null)
                taskBox.setTaskDescription(updateTaskBoxDetails.getTaskDescription());
            if (updateTaskBoxDetails.getTotalDays() > 0)
                taskBox.setTotalDays(updateTaskBoxDetails.getTotalDays());

            if (updateTaskBoxDetails.getStatus() != null)  // Update status if provided
                taskBox.setStatus(updateTaskBoxDetails.getStatus());

            // Save updated entity
            TaskBox updatedTaskBox = taskBoxRepository.save(taskBox);
            return Optional.of(updatedTaskBox);
        }

        return Optional.empty();
    }

    public List<TaskBox> findTasksByEmployeeName(String employeeName) {
        return taskBoxRepository.findByEmployeeName(employeeName);
    }

    // Method to get tasks by status
    public List<TaskBox> findTasksByStatus(String status) {
        return taskBoxRepository.findByStatus(status);
    }

    // Method to get tasks by task description
    public List<TaskBox> findTasksByDescription(String taskDescription) {
        return taskBoxRepository.findByTaskDescriptionContaining(taskDescription);
    }


}
