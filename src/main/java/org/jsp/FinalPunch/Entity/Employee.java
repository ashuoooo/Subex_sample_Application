package org.jsp.FinalPunch.Entity;

import jakarta.persistence.*;
import org.jsp.FinalPunch.Enum.Role;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String employeeName;

    @Column(unique = true)
    private String emailId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = true)
    private String mobileNumber;

    private String headOfDepartment;

    private Date dateOfJoining;

    private String company;

    private String businessUnit;

    private String hrbp; // Corrected from hrbpRole to hrbp

    private String manager;

    // One-to-Many Relationships
    // Uncomment these if needed for relationships
    // @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<Attendance> attendanceList;
    //
    // @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<TaskBox> taskBoxList;
    //
    // @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<Leaves> leavesList;

    public Employee() {
    }

    public Employee(int id, String employeeName, String emailId, Role role, String mobileNumber, String headOfDepartment, Date dateOfJoining, String company, String businessUnit, String hrbp, String manager) {
        this.id = id;
        this.employeeName = employeeName;
        this.emailId = emailId;
        this.role = role;
        this.mobileNumber = mobileNumber;
        this.headOfDepartment = headOfDepartment;
        this.dateOfJoining = dateOfJoining;
        this.company = company;
        this.businessUnit = businessUnit;
        this.hrbp = hrbp;  // Corrected
        this.manager = manager;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        String regex="^[0-9]{10}$";
      try{

          if(mobileNumber.isEmpty()){
              this.mobileNumber="N.A";
          }
          else if(!mobileNumber.matches(regex)){
              throw new IllegalArgumentException("Invalid mobile Number . It must be Exactly 10 digits and in Number Format");
          }else {
              this.mobileNumber = mobileNumber;
          }
      }
      catch (IllegalArgumentException e){
          System.out.println(e.getMessage());
          throw e;
      }
    }

    public String getHeadOfDepartment() {
        return headOfDepartment;
    }

    public void setHeadOfDepartment(String headOfDepartment) {
        this.headOfDepartment = headOfDepartment;
    }

    public Date getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(Date dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public String getHrbp() {
        return hrbp;  // Corrected getter for hrbp
    }

    public void setHrbp(String hrbp) {
        this.hrbp = hrbp;  // Corrected setter for hrbp
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    // Uncomment these methods if the relationships are needed
    // public List<Attendance> getAttendanceList() {
    //     return attendanceList;
    // }
    //
    // public void setAttendanceList(List<Attendance> attendanceList) {
    //     this.attendanceList = attendanceList;
    // }
    //
    // public List<TaskBox> getTaskBoxList() {
    //     return taskBoxList;
    // }
    //
    // public void setTaskBoxList(List<TaskBox> taskBoxList) {
    //     this.taskBoxList = taskBoxList;
    // }
    //
    // public List<Leaves> getLeavesList() {
    //     return leavesList;
    // }
    //
    // public void setLeavesList(List<Leaves> leavesList) {
    //     this.leavesList = leavesList;
    // }
}
