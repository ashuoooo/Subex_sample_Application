package org.jsp.FinalPunch.Repository;

import org.jsp.FinalPunch.Entity.TaskBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public interface TaskBoxRepository extends JpaRepository<TaskBox ,Integer> {


    // Custom query to find tasks by employee name
    @Query("SELECT t FROM TaskBox t WHERE t.employeeName = :employeeName")
    List<TaskBox> findByEmployeeName(@Param("employeeName") String employeeName);

    // Custom query to find tasks with a specific status
    @Query("SELECT t FROM TaskBox t WHERE t.status = :status")
    List<TaskBox> findByStatus(@Param("status") String status);

    // Custom query to find tasks with specific task description
    @Query("SELECT t FROM TaskBox t WHERE t.taskDescription LIKE %:taskDescription%")
    List<TaskBox> findByTaskDescriptionContaining(@Param("taskDescription") String taskDescription);

}
