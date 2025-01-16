package org.jsp.FinalPunch.Dto;

import org.springframework.http.HttpStatus;

public class ApiResponse<T> {

    private T body;
    private String description;

    public ApiResponse() {
    }

    // Constructor for successful responses is done
    public ApiResponse(T body, String description) {
        this.body = body;
        this.description = description;
    }

    // Constructor for responses with only body (no description)
    public ApiResponse(T body) {
        this.body = body;
    }

    // Constructor for responses with only description (no body)
    public ApiResponse(String description) {
        this.description = description;
    }

    // Constructor for internal server error responses with default description
    public ApiResponse(HttpStatus httpStatus) {
        if (httpStatus == HttpStatus.INTERNAL_SERVER_ERROR) {
            this.description = "An internal server error occurred. Please try again later.";
        }
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Overriding toString() method to provide string representation of the object
    @Override
    public String toString() {
        return "ApiResponse{" +
                "body=" + body +
                ", description='" + description + '\'' +
                '}';
    }
}
