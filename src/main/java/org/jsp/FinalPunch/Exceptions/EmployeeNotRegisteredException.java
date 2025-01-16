package org.jsp.FinalPunch.Exceptions;

import org.springframework.stereotype.Component;

public class EmployeeNotRegisteredException extends RuntimeException{
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
   public EmployeeNotRegisteredException(String message){
       this.message=message;
   }



    @Override
    public String toString() {
        return "EmployeeNotRegisteredException{" +
                "message='" + getMessage() + '\'' +
                '}';
    }
}
