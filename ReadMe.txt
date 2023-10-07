we use @RestController annotation to tell spring that this class is a rest controller


Data binding is the procee of converting JSON data to Java POJO
Data binding is also known as: Mapping, Serilization / deserilization, marshalling / unmarshalling 

Spring uses Jackson Project behind the scene for data binding:
Jackson provides the functionality to perform these conversions.

Spring uses Jackson by integrating it with the Spring MVC framework. 
Specifically, Spring provides a `MappingJackson2HttpMessageConverter` class that can be used to convert between JSON and Java objects.
This converter is automatically registered with the Spring MVC framework, so it can be used by Spring MVC controllers without any additional configuration.
To use Jackson with Spring, you'll need to include the Jackson dependencies in your project and configure your Spring application to use the `MappingJackson2HttpMessageConverter`.
 

Converting JSON to JAVA POJO: Jackson calls setter methods on POJO
Converting Java POJO to JSON: Jackson calls getter methods on POJO






here are the steps in the lifecycle of a Spring MVC application with StudentRestController:

1. The Spring application starts, and the Spring container initializes.

2. During initialization, Spring scans for annotated components, including StudentRestController, and instantiates the controller.

3. The @PostConstruct annotated loadData method is automatically invoked, initializing the theStudents list with data.

4. The application is now running, and when a user sends a GET request to /api/students, Spring routes the request to the getStudents method in StudentRestController.

5. The getStudents method is executed, and it returns the list of students as JSON/XML (based on configuration).

6. Spring serializes the data and sends it as an HTTP response to the user's browser or client.

7. The client receives and displays the list of students.






In step 2, during the initialization of the Spring container, the framework searches for various annotations to identify and configure components. 
Specifically, it looks for annotations like:

@Controller: To identify classes as controllers.
@Service: To identify classes as service beans.
@Repository: To identify classes as repository beans.
@Component: A generic annotation for identifying Spring-managed components.



for doing the exception handling on this project, we did the following:
1. we created a StudentErrorResponse class which has 2 fields: status and message. (this class is a POJO and Jackson converts it to json) // we know it is a POJO because it has private fields, getters and setters.
2. we created a StudentNotFoundException class which extends RuntimeException class.
    2.1 we created a default constructor for this class.
    2.2 we generated constructors with parameters:
        - String message
        - Throwable cause
        - String message, Throwable cause
    with these 3 parameters, because we want to be able to pass a message and a cause to the constructor of this class.
    the cause example is: searching for a student with an id that does not exist in the database.
3. we updated the REST service (StudentRestController) to throw the StudentNotFoundException if the student is not found:
    3.1 in getStudent method, we added the following code:
        if (studentId > theStudents.size() || (studentId < 0)) {
                   throw new StudentNotFoundException("Student id not found - " + studentId);
                }
    3.2 add an exception handler method using @ExceptionHandler annotation:
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



Break down of the @ExceptionHandler method:

    // @ExceptionHandler Annotation: This annotation marks the method as an exception handler.
    @ExceptionHandler(StudentNotFoundException.class)

    // this method will return an instance of ResponseEntity containing an object of type StudentErrorResponse
    // ResponseEntity is a class by the Spring Framework, used to customize the HTTP response status code, headers, and body.
    // Here, we're using it to return a custom error response body and a status code of 404.
    public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exception) {


        //    creating an instane of StudentErrorResponse class which  has 3 fields: status, message and timeStamp
        //    we will set the values of these fields in the following lines of code:
        //    This object will store the error details.
        StudentErrorResponse error = new StudentErrorResponse();


        // to set the status code, we use the setStatus method of the StudentErrorResponse class
        // the parameter of this method is the status code that we want to set. (HttpStatus.NOT_FOUND.value = 404)
        error.setStatus(HttpStatus.NOT_FOUND.value());


        // to set the message, we use the setMessage method of the StudentErrorResponse class
        error.setMessage(exception.getMessage());



        or we can use the following code:
        error.setMessage("Please enter a valid student id"); //  customize the message


        // to set the timeStamp, we use the setTimeStamp method of the StudentErrorResponse class
        error.setTimeStamp(System.currentTimeMillis());


        // returning a new RosponseEntity object with the following parameters:
        // 1. the error object that we created above
        // 2. the status code that we want to return
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
