package org.jsp.FinalPunch.Repository;

import org.jsp.FinalPunch.Entity.Employee;
import org.jsp.FinalPunch.Enum.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public interface EmployeeRepository extends JpaRepository<Employee , Integer> {
//
//    @Query("SELECT u FROM User u Where u.role=:role")
//    Employee findByRole(@Param("role") Role role);

    @Query("SELECT e FROM Employee e WHERE e.role = :role")
    List<Employee> findEmployeesByRole(@Param("role") Role role);
}
