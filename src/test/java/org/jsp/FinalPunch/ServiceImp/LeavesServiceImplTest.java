package org.jsp.FinalPunch.Service.ServiceImp;

import org.jsp.FinalPunch.Entity.Employee;
import org.jsp.FinalPunch.Entity.Leaves;
import org.jsp.FinalPunch.Repository.EmployeeRepository;
import org.jsp.FinalPunch.Repository.LeavesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LeavesServiceImplTest {

    @Mock
    private LeavesRepository leavesRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private LeavesServiceImpl leavesService;

    private Employee employee;
    private Leaves leaves;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        employee = new Employee();
        employee.setId(1);
        employee.setEmployeeName("John Doe");

        leaves = new Leaves();
        leaves.setId(1);
        leaves.setEmployee(employee);
        leaves.setEmployeeName(employee.getEmployeeName());
        leaves.setCasualLeaves(5);
        leaves.setEarnedLeaves(10);
        leaves.setTotalLeaves(30);
    }

    @Test
    public void testSaveEmployeeLeaves() {
        when(employeeRepository.findById(1)).thenReturn(java.util.Optional.of(employee));
        when(leavesRepository.save(leaves)).thenReturn(leaves);

        Leaves savedLeaves = leavesService.saveEmployeeLeaves(leaves);

        assertNotNull(savedLeaves);
        assertEquals(5, savedLeaves.getCasualLeaves());
        assertEquals(10, savedLeaves.getEarnedLeaves());
    }

    @Test
    public void testSaveEmployeeLeaves_EmployeeNotFound() {
        when(employeeRepository.findById(1)).thenReturn(java.util.Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            leavesService.saveEmployeeLeaves(leaves);
        });

        assertEquals("Employee not found with ID: 1", exception.getMessage());
    }



    @Test
    public void testDeleteEmployeeLeavesById() {
        when(leavesRepository.findById(1)).thenReturn(java.util.Optional.of(leaves));

        Optional<Leaves> deletedLeaves = leavesService.deleteEmployeeLeavesById(1);

        assertTrue(deletedLeaves.isPresent());
        verify(leavesRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteEmployeeLeavesById_LeavesNotFound() {
        when(leavesRepository.findById(1)).thenReturn(java.util.Optional.empty());

        Optional<Leaves> deletedLeaves = leavesService.deleteEmployeeLeavesById(1);

        assertFalse(deletedLeaves.isPresent());
    }

    @Test
    public void testFindEmployeeLeavesById() {
        when(leavesRepository.findById(1)).thenReturn(java.util.Optional.of(leaves));

        Optional<Leaves> foundLeaves = leavesService.findEmployeeLeavesbYId(1);

        assertTrue(foundLeaves.isPresent());
        assertEquals("John Doe", foundLeaves.get().getEmployeeName());
    }

    @Test
    public void testFindEmployeeLeavesById_LeavesNotFound() {
        when(leavesRepository.findById(1)).thenReturn(java.util.Optional.empty());

        Optional<Leaves> foundLeaves = leavesService.findEmployeeLeavesbYId(1);
        assertFalse(foundLeaves.isPresent());
    }
}
