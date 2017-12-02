package com.bala.resource;

import com.bala.model.CallCenterRequest;
import com.bala.validator.ValidateRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by hp on 11/30/2017.
 */
@RestController
@RequestMapping("/api")
public class CallCenterResource {

    @RequestMapping(value = "/hello/", method = RequestMethod.POST)
    public CallCenterRequest createUser(@ValidateRequest @Valid  @RequestBody CallCenterRequest userVO) {
        System.out.println("Hello");
        return userVO;
    }
}
