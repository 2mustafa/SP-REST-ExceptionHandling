package com.mustafa.restapi.rest;

import com.mustafa.restapi.entity.Student;
import com.mustafa.restapi.myErrorHandling.StudentErrorResponse;
import com.mustafa.restapi.myErrorHandling.StudentNotFoundException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping ("/api")
public class StudentRestController {

    private List<Student> theStudents;
    @PostConstruct
    public void loadData(){
        theStudents = new ArrayList<>();

        theStudents.add(new Student("John", "Doe"));
        theStudents.add(new Student("Jack", "Smith"));
        theStudents.add(new Student("Mary", "Müller"));

    }



    // define an endpoint for "/student"

    @GetMapping ("/students")
    public List<Student> getStudents(){


        return theStudents;
    }




    // define an endpoint  ("/students/{studentID}") for returning a specific student

    @GetMapping ("/students/{studentId}")
    public Student getStudent(@PathVariable int studentId){

        // check the studentId against the list size

        if (studentId > theStudents.size() || (studentId < 0)) {
           throw new StudentNotFoundException("Student id not found - " + studentId);
        }

        // returning the student based on the index only (if it passed the if statement)
        return theStudents.get(studentId);
    }


    // add adn exception handler using @ExceptionHandler
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException (StudentNotFoundException exception){

        // 1. create a StudentErrorResponse

        StudentErrorResponse error = new StudentErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exception.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        // 2. return RosponseEntity
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    // add adn exception handler to catch any Exception
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException (Exception exception){

        // 1. create a StudentErrorResponse

        StudentErrorResponse error = new StudentErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
     // error.setMessage(exception.getMessage()); // gets the original message
        error.setMessage("Please enter a valid student id"); //  customize the message
        error.setTimeStamp(System.currentTimeMillis());

        // 2. return RosponseEntity
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


}
