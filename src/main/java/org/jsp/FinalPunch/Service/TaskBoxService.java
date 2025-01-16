package org.jsp.FinalPunch.Service;

import org.jsp.FinalPunch.Service.ServiceImp.TaskBoxServiceImp;
import org.jsp.FinalPunch.Entity.TaskBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
@Service
public class TaskBoxService {
    @Autowired
    private TaskBoxServiceImp taskBoxServiceImp;
    public List<TaskBox> findallTaskBoxService() {
        return taskBoxServiceImp.findallEmployeeTasks();
    }
    public TaskBox saveEmployeeTaskBox(TaskBox taskBox) {
        return taskBoxServiceImp.saveEmployeeTasks(taskBox);
    }

    public Optional<TaskBox> findEmployeeTaskById(int id) {
        return taskBoxServiceImp.findEmployeeTaskBoxbYId(id);
    }

    public Optional<TaskBox> deleteEmployeeTaskId(int id) {
        return taskBoxServiceImp.deleteEmployeeTaskById(id);
    }

    public Optional<TaskBox> updateEmployeeTaskBoxId(int id, TaskBox updateEmployeeTaskDetails) {
        return taskBoxServiceImp.updateTaskBox(id, updateEmployeeTaskDetails);
    }

    public List<TaskBox> findTasksByEmployeeName(String employeeName) {
        return taskBoxServiceImp.findTasksByEmployeeName(employeeName);
    }

    public List<TaskBox> findTasksByStatus(String status) {
        return taskBoxServiceImp.findTasksByStatus(status);
    }

    public List<TaskBox> findTasksByDescription(String taskDescription) {
        return taskBoxServiceImp.findTasksByDescription(taskDescription);
    }

}
