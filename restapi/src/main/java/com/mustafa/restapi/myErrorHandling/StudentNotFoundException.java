package com.mustafa.restapi.myErrorHandling;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException() {
    }



    public StudentNotFoundException(String message) {
        super(message);
    }

    public StudentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentNotFoundException(Throwable cause) {
        super(cause);
    }
}
