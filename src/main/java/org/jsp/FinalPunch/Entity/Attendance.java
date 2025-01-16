package org.jsp.FinalPunch.Entity;

import jakarta.persistence.*;
import org.jsp.FinalPunch.Enum.PresentStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;

import java.util.Date;

@Entity
@Component
public class Attendance {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private Date date;

    private String employeeName;

    @Enumerated(EnumType.STRING)
    private PresentStatus presentStatus;

    private int totalPresent;
    private int totalAbsent;

    public Attendance(int id, Employee employee, Date date, String employeeName, PresentStatus presentStatus, int totalPresent, int totalAbsent, int totalLeaves) {
        this.id = id;
        this.employee = employee;
        this.date = date;
        this.employeeName = employeeName;
        this.presentStatus = presentStatus;
        this.totalPresent = totalPresent;
        this.totalAbsent = totalAbsent;
        this.totalLeaves = totalLeaves;
    }

    private int totalLeaves;
//
//    @ManyToOne
//    @JoinColumn(name = "leave_id", referencedColumnName = "id")
//    private Leaves leave;

    // Constructors, getters and setters

    public Attendance() {}



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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public PresentStatus getPresentStatus() {
        return presentStatus;
    }

    public void setPresentStatus(PresentStatus presentStatus) {
        this.presentStatus = presentStatus;
    }

    public int getTotalPresent() {
        return totalPresent;
    }

    public void setTotalPresent(int totalPresent) {
        this.totalPresent = totalPresent;
    }

    public int getTotalAbsent() {
        return totalAbsent;
    }

    public void setTotalAbsent(int totalAbsent) {
        this.totalAbsent = totalAbsent;
    }

    public int getTotalLeaves() {
        return totalLeaves;
    }

    public void setTotalLeaves(int totalLeaves) {
        this.totalLeaves = totalLeaves;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
//    public Leaves getLeave() {
//        return leave;
//    }
//
//    public void setLeave(Leaves leave) {
//        this.leave = leave;
//    }
}
