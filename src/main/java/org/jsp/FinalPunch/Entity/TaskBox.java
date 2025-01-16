package org.jsp.FinalPunch.Entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Component
@Entity
public class TaskBox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private String employeeName;
    private String taskName;
    private String taskDescription;
    private String status; // Add status field
    private int totalDays;

    public TaskBox() {
    }

    public TaskBox(int id, Employee employee, String employeeName, String taskName, String taskDescription, int totalDays, String status) {
        this.id = id;
        this.employee = employee;
        this.employeeName = employeeName;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.totalDays = totalDays;
        this.status = status; // Set status in constructor
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public String getStatus() {
        return status; // Getter for status
    }

    public void setStatus(String status) {
        this.status = status; // Setter for status
    }
}
