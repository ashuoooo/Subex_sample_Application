package org.jsp.FinalPunch.ServiceImp;
import org.jsp.FinalPunch.Entity.Employee;
import org.jsp.FinalPunch.Entity.TaskBox;
import org.jsp.FinalPunch.Repository.EmployeeRepository;
import org.jsp.FinalPunch.Repository.TaskBoxRepository;
import org.jsp.FinalPunch.Service.ServiceImp.TaskBoxServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
        import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@SpringBootTest
public class TaskBoxServiceImpTest {

    @Mock
    private TaskBoxRepository taskBoxRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private TaskBoxServiceImp taskBoxServiceImp;

    private Employee employee;
    private TaskBox taskBox;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        employee = new Employee();
        employee.setId(1);
        employee.setEmployeeName("John Doe");

        taskBox = new TaskBox();
        taskBox.setId(1);
        taskBox.setEmployee(employee);
        taskBox.setEmployeeName("John Doe");
        taskBox.setTaskName("Task 1");
        taskBox.setTaskDescription("Description of Task 1");
        taskBox.setTotalDays(5);
        taskBox.setStatus("Pending");
    }

    @Test
    public void testSaveEmployeeTasks_WhenEmployeeExists() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(taskBoxRepository.save(any(TaskBox.class))).thenReturn(taskBox);

        TaskBox savedTaskBox = taskBoxServiceImp.saveEmployeeTasks(taskBox);

        assertNotNull(savedTaskBox);
        assertEquals("Task 1", savedTaskBox.getTaskName());
        verify(taskBoxRepository, times(1)).save(any(TaskBox.class));
    }

    @Test
    public void testSaveEmployeeTasks_WhenEmployeeNotFound() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            taskBoxServiceImp.saveEmployeeTasks(taskBox);
        });

        assertEquals("Employee not found with ID: 1", exception.getMessage());
    }

    @Test
    public void testFindEmployeeTaskBoxbYId() {
        when(taskBoxRepository.findById(1)).thenReturn(Optional.of(taskBox));

        Optional<TaskBox> foundTaskBox = taskBoxServiceImp.findEmployeeTaskBoxbYId(1);

        assertTrue(foundTaskBox.isPresent());
        assertEquals("Task 1", foundTaskBox.get().getTaskName());
    }

    @Test
    public void testDeleteEmployeeTaskById_WhenTaskExists() {
        when(taskBoxRepository.findById(1)).thenReturn(Optional.of(taskBox));

        Optional<TaskBox> deletedTaskBox = taskBoxServiceImp.deleteEmployeeTaskById(1);

        assertTrue(deletedTaskBox.isPresent());
        verify(taskBoxRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteEmployeeTaskById_WhenTaskNotFound() {
        when(taskBoxRepository.findById(1)).thenReturn(Optional.empty());

        Optional<TaskBox> deletedTaskBox = taskBoxServiceImp.deleteEmployeeTaskById(1);

        assertFalse(deletedTaskBox.isPresent());
        verify(taskBoxRepository, times(0)).deleteById(1);
    }


}
