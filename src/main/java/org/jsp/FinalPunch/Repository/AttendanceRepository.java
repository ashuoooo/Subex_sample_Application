package org.jsp.FinalPunch.Repository;

import org.jsp.FinalPunch.Entity.Attendance;
import org.jsp.FinalPunch.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Component
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Integer> {
    // Custom query to count attendance records for the employee in the last 24 hours
//    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.employee.id = :employeeId AND a.date > :date")
//    int countAttendanceInLast24Hours(@Param("employeeId") int employeeId, @Param("date") LocalDateTime date);
   //    Attendance findFirstByEmployeeOrderByDateDesc(Employee employee);
    Optional<Attendance> findTopByEmployeeAndDateOrderByIdDesc(Employee employee, Date date);

    Optional<Attendance> findTopByEmployeeOrderByIdDesc(Employee employee1);


    // Optional<Attendance> findByEmployeeAndDate(Employee employee, Date date);
//    LocalDate today = LocalDate.now();
//    return attendanceRepository.findTopByEmployeeAndDateOrderByAttendanceIdDesc(employee, today);
     //   return attendanceRepository.findTopByEmployeeAndDateOrderByAttendanceIdDesc(employee, date);


}
