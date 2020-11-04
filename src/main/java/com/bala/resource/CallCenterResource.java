package com.bala.resource;

import com.bala.model.CallCenterRequest;
import com.bala.model.CallCenterResponse;
import com.bala.service.CallCenterService;
import com.bala.validator.ValidateRequest;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * Created by hp on 12/3/2017.
 */
@RestController
@RequestMapping("/api")
@EnableSwagger2
@Api(value="callcenterapp", description="API to calculate the overall performance of Call Center")
public class CallCenterResource {

    @Autowired
    CallCenterService service;

    @RequestMapping(value = "/performance/", method = RequestMethod.POST)
    public CallCenterResponse calculatePerformance( @Valid @ValidateRequest  @RequestBody CallCenterRequest userVO) {
        return service.calculatePerformance(userVO);
    }
  
  @RequestMapping(value = "/mds", method = RequestMethod.GET)
  @ResponseBody
  public String mds(@RequestBody  String raw, HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
	 // Thread.sleep(1000000);
     System.out.println(" Called post " +raw);
   // response.setStatus(500);
     return "Bala";
  }
}
