package org.jsp.FinalPunch.ServiceImp;

import org.jsp.FinalPunch.Entity.Attendance;
import org.jsp.FinalPunch.Entity.Employee;
import org.jsp.FinalPunch.Repository.AttendanceRepository;
import org.jsp.FinalPunch.Repository.EmployeeRepository;
import org.jsp.FinalPunch.Repository.LeavesRepository;
import org.jsp.FinalPunch.Service.ServiceImp.AttendanceServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AttendanceServiceImpTest {


    @InjectMocks
    private AttendanceServiceImp attendanceServiceImp;

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private LeavesRepository leavesRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindEmployeeDaoAttendanceBYId() {
        int attendanceId = 1;
        Attendance attendance = new Attendance();
        attendance.setId(attendanceId);

        when(attendanceRepository.findById(attendanceId)).thenReturn(Optional.of(attendance));

        Optional<Attendance> result = attendanceServiceImp.findEmployeeDaoAttendanceBYId(attendanceId);

        assertTrue(result.isPresent());
        assertEquals(attendanceId, result.get().getId());
        verify(attendanceRepository, times(1)).findById(attendanceId);
    }



    @Test
    void testSaveEmployeeDaoAttendance_EmployeeNotExists() {
        int employeeId = 1;
        Attendance attendance = new Attendance();
        Employee employee = new Employee();
        employee.setId(employeeId);
        attendance.setEmployee(employee);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            attendanceServiceImp.saveEmployeeDaoAttendance(attendance);
        });

        assertEquals("Employee not found with ID: 1", exception.getMessage());
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(attendanceRepository, never()).save(any(Attendance.class));
    }


    @Test
    void testDeleteEmployeeDaoAttendanceById() {
        int attendanceId = 1;
        Attendance attendance = new Attendance();
        attendance.setId(attendanceId);

        when(attendanceRepository.findById(attendanceId)).thenReturn(Optional.of(attendance));

        Optional<Attendance> result = attendanceServiceImp.deleteEmployeeDaoAttendanceById(attendanceId);

        assertTrue(result.isPresent());
        verify(attendanceRepository, times(1)).findById(attendanceId);
        verify(attendanceRepository, times(1)).deleteById(attendanceId);
    }

    @Test
    void testDeleteEmployeeDaoAttendanceById_NotFound() {
        int attendanceId = 1;

        when(attendanceRepository.findById(attendanceId)).thenReturn(Optional.empty());

        Optional<Attendance> result = attendanceServiceImp.deleteEmployeeDaoAttendanceById(attendanceId);

        assertFalse(result.isPresent());
        verify(attendanceRepository, times(1)).findById(attendanceId);
        verify(attendanceRepository, never()).deleteById(attendanceId);
    }
}