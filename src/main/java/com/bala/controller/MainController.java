package com.bala.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hp on 11/29/2017.
 */
@RestController
@RequestMapping("/api")
public class MainController {

    @RequestMapping(value = "/hello/", method = RequestMethod.GET)
    public ResponseEntity<String> hello(){
        return new ResponseEntity<String>("Balaga", HttpStatus.OK);
    }
}


