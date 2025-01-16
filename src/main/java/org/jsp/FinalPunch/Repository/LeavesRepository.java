package org.jsp.FinalPunch.Repository;

import org.jsp.FinalPunch.Entity.Employee;
import org.jsp.FinalPunch.Entity.Leaves;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Component
@Repository
public interface LeavesRepository extends JpaRepository<Leaves , Integer> {
    Optional<Leaves> findByEmployee(Employee employee);


}
