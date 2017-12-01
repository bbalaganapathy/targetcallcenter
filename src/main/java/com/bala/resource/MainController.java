package com.bala.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hp on 11/29/2017.
 */
@RestController

public class MainController {
    @RequestMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String home() {
        return "Nothing here.";
    }

    @RequestMapping(value = "/hello/", method = RequestMethod.GET)
    public ResponseEntity<String> hello(){
        return new ResponseEntity<String>("Balaga", HttpStatus.OK);
    }
}


