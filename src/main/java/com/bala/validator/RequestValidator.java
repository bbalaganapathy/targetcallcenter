package com.bala.validator;

import com.bala.model.CallCenterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by hp on 12/3/2017.
 */


public class RequestValidator implements ConstraintValidator<ValidateRequest, CallCenterRequest> {

    @Autowired
    Environment env;

    private MessageSource messageSource;

    @Override
    public void initialize(ValidateRequest constraintAnnotation) {

    }

    @Override
    public boolean isValid(CallCenterRequest value, ConstraintValidatorContext context) {
        if (value == null) return false;
        if (value.getJe().size() < value.getSe().size()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{je.greater.se.message}")
                    .addPropertyNode("je").addConstraintViolation();
            return false;
        }

        if(!verifyCallsHandled(value,true)){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{je.morecalls.message}")
                    .addPropertyNode("je").addConstraintViolation();
            return false;
        }
        if(!verifyCallsHandled(value,false)){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{se.morecalls.message}")
                    .addPropertyNode("se").addConstraintViolation();
            return false;
        }

        if(verifyTotalCallsWithJE(value)!=null){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(verifyTotalCallsWithJE(value))
                    .addPropertyNode("je").addConstraintViolation();
            return false;
        }
        if(verifyTotalCallsWithSE(value)!=null){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(verifyTotalCallsWithSE(value))
                    .addPropertyNode("se").addConstraintViolation();
            return false;
        }

        if(verifyTotalCallsWithManager(value)!=null){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(verifyTotalCallsWithManager(value))
                    .addPropertyNode("mgr").addConstraintViolation();
            return false;
        }
        return true;
    }


    private int totalCalls(CallCenterRequest req){
        int totalCalls=0;
       // totalCalls+=req.getJe().length+req.getSe().length+req.getMgr().split(",").length;
        return  totalCalls;
    }

    private boolean verifyCallsHandled(CallCenterRequest req,boolean isJunior){
        int totalCalls = req.getNoOfCalls();

       // int maxCalls=Math.abs(totalCalls/ (req.getJe().size()+req.getSe().size()));
        int maxCalls= (int) Math.ceil((double)totalCalls / (req.getJe().size()));//+req.getSe().size()));
        if(isJunior){
            for(String exe:req.getJe()){
                if(exe.split(",").length > maxCalls){
                    return false;
                }
            }
        }else{
            int maxCalls1= (int) Math.ceil((double)totalCalls / (req.getSe().size()));//+req.getSe().size()));
            for(String exe:req.getSe()){
                if(exe.split(",").length > maxCalls1){
                    return false;
                }
            }
        }
        return true;
    }

    private String verifyTotalCallsWithJE(CallCenterRequest req){
        int totalCalls = req.getNoOfCalls();
        int expectedCount=0;
        for(String v:req.getJe())
            expectedCount+=v.split(",").length;
        if(expectedCount!=totalCalls)
            return "Totalcalls "+totalCalls+" is not to equal to calls handled by all Junior executive";
        return null;
    }

    private String verifyTotalCallsWithSE(CallCenterRequest req){
         int expectedCount=0;
        int actualCount=0;
        for(String je:req.getJe()){
            for(String durString:je.split(",")){
               if(Integer.valueOf(durString) >Integer.parseInt(env.getProperty("junior.escalation.time")))
                   expectedCount++;
            }
        }
        for(String se:req.getSe()){
            actualCount+=se.split(",").length;
        }

        if(expectedCount!=actualCount)
            return "Totalcalls "+actualCount+" taken by Senior is greater than "+expectedCount+" which are escalated by Junior";
        return null;
    }
    private String verifyTotalCallsWithManager(CallCenterRequest req){
        int expectedCount=0;
        int actualCount=req.getMgr().split(",").length;
        for(String se:req.getSe()){
            for(String durString:se.split(",")){
                if(Integer.valueOf(durString) > Integer.parseInt(env.getProperty("senior.escalation.time")))
                    expectedCount++;
            }
        }
        if(expectedCount!=actualCount)
            return "Totalcalls "+actualCount+" taken by Manager is greater than "+expectedCount+" which are escalated by Senior";
        return null;
    }
}
