package org.jsp.FinalPunch.Exceptions;

public class AttendanceAlreadyRegisteredException extends RuntimeException{

   private  String message;

    @Override
    public String toString() {
        return "AttendanceAlreadyRegisteredException{" +
                "message='" + message + '\'' +
                '}';
    }

    public AttendanceAlreadyRegisteredException(String message) {
        super(message);
        this.message = message;
    }
}
