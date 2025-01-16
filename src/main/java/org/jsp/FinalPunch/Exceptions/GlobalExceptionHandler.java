package org.jsp.FinalPunch.Exceptions;

import org.jsp.FinalPunch.Dto.ApiResponse;
import org.jsp.FinalPunch.Entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotRegisteredException.class)
    public ResponseEntity<ApiResponse<String>> handleEmployeeNotFoundException(EmployeeNotRegisteredException ex){
        ApiResponse<String> apiResponse=new ApiResponse<>(
              // HttpStatus.NOT_FOUND,
               // null,
                ex.getMessage()
        );
        return new ResponseEntity<>(apiResponse , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AttendanceAlreadyRegisteredException.class)
    public ResponseEntity<ApiResponse<String>> handleAttendanceAlreadyFoundException(EmployeeNotRegisteredException ex){
        ApiResponse<String> apiResponse=new ApiResponse<>(
                // HttpStatus.NOT_FOUND,
                // null,
                ex.getMessage()
        );
        return new ResponseEntity<>(apiResponse , HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGeneralException(Exception ex) {
        ApiResponse<String> apiResponse = new ApiResponse<>(
               //HttpStatus.INTERNAL_SERVER_ERROR,
             //   null,
                "An unexpected error occurred: " + ex.getMessage()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
