package org.jsp.FinalPunch.Entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Component
@Entity
public class Leaves {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private String employeeName;

    private int casualLeaves;

    private int earnedLeaves;

    private int totalLeaves;  // Total leaves granted (e.g., 120 for an employee)

    private int totalLeavesKept;  // Total leaves used (casualLeaves + earnedLeaves)

    private int totalLeavesRemaining;  // Calculated from totalLeaves - totalLeavesKept

    public Leaves() {
    }

    public Leaves(int id, Employee employee, String employeeName, int casualLeaves, int earnedLeaves, int totalLeaves) {
        this.id = id;
        this.employee = employee;
        this.employeeName = employeeName;
        this.casualLeaves = casualLeaves;
        this.earnedLeaves = earnedLeaves;
        this.totalLeaves = totalLeaves;
        this.totalLeavesKept = casualLeaves + earnedLeaves;  // Calculate total leaves used
        this.totalLeavesRemaining = totalLeaves - this.totalLeavesKept;  // Calculate remaining leaves
    }

    // Getters and setters

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

    public int getCasualLeaves() {
        return casualLeaves;
    }

    public void setCasualLeaves(int casualLeaves) {
        this.casualLeaves = casualLeaves;
        updateLeaves();
    }

    public int getEarnedLeaves() {
        return earnedLeaves;
    }

    public void setEarnedLeaves(int earnedLeaves) {
        this.earnedLeaves = earnedLeaves;
        updateLeaves();
    }

    public int getTotalLeaves() {
        return totalLeaves;
    }

    public void setTotalLeaves(int totalLeaves) {
        this.totalLeaves = totalLeaves;
        updateLeaves();
    }

    public int getTotalLeavesKept() {
        return totalLeavesKept;
    }

    public void setTotalLeavesKept(int totalLeavesKept) {
        this.totalLeavesKept = totalLeavesKept;
        updateRemainingLeaves();
    }

    public int getTotalLeavesRemaining() {
        return totalLeavesRemaining;
    }

    public void setTotalLeavesRemaining(int totalLeavesRemaining) {
        this.totalLeavesRemaining = totalLeavesRemaining;
    }

    // Method to update totalLeavesKept and totalLeavesRemaining when casualLeaves or earnedLeaves are updated
    private void updateLeaves() {
        this.totalLeavesKept = this.casualLeaves + this.earnedLeaves;  // Total leaves used (casual + earned)
        updateRemainingLeaves();
    }

    // Method to update remaining leaves after totalLeavesKept is updated
    private void updateRemainingLeaves() {
        this.totalLeavesRemaining = this.totalLeaves - this.totalLeavesKept;  // Calculate remaining leaves
    }
}
