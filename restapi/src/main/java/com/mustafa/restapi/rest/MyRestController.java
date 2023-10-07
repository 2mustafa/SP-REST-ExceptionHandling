package com.mustafa.restapi.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;





@RestController // this annotation tells spring that this class is a rest controller
@RequestMapping ("test")
public class MyRestController {

    @GetMapping ("/hello")
    public String sayhello(){
        return "hello World";
    }


}
