package com.bala.validator;

import com.bala.model.CallCenterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

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
        //Validation to check negative duration for junior.
        if(value.getJe().stream().flatMap(i->i.stream()).filter(j->j<0).count()>0){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{je.negative.message}")
                    .addPropertyNode("je").addConstraintViolation();
            return false;
        }
        //Validation to check negative duration for senior.
        if(value.getSe().stream().flatMap(i->i.stream()).filter(j->j<0).count()>0){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{se.negative.message}")
                    .addPropertyNode("se").addConstraintViolation();
            return false;
        }
        //Validation to check je<se.
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
        String msg=verifyTotalCallsWithSE(value);
        if(msg!=null){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(msg)
                    .addPropertyNode("se").addConstraintViolation();
            return false;
        }
        String msgMgr=verifyTotalCallsWithSE(value);
        if(msgMgr!=null){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(msgMgr)
                    .addPropertyNode("mgr").addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean verifyCallsHandled(CallCenterRequest req,boolean isJunior){
        int totalCalls = req.getNoOfCalls();
        int maxCalls= (int) Math.ceil((double)totalCalls / (req.getJe().size()));
        if(isJunior){
            for(List<Integer> exe:req.getJe()){
                if(exe.size() > maxCalls){
                    return false;
                }
            }
        }else{
            int maxCalls1= (int) Math.ceil((double)totalCalls / (req.getSe().size()));
            for(List<Integer> exe:req.getSe()){
                if(exe.size() > maxCalls1){
                    return false;
                }
            }
        }
        return true;
    }

   private String verifyTotalCallsWithJE(CallCenterRequest req){
        int totalCalls = req.getNoOfCalls();
        int expectedCount=0;
        for(List<Integer> v:req.getJe())
            expectedCount+=v.size();
        if(expectedCount!=totalCalls)
            return String.format("Total calls %1$s are not equal to the calls handled by all Junior executives.", totalCalls);
        return null;
    }

   private String verifyTotalCallsWithSE(CallCenterRequest req){
         int expectedCount=0;
        int actualCount=0;
        for(List<Integer> juniorList:req.getJe()){
            for(Integer i:juniorList){
               if(i >Integer.parseInt(env.getProperty("junior.escalation.time")))
                   expectedCount++;
            }
        }
        for(List<Integer> seniorList:req.getSe()){
                    actualCount+=seniorList.size();
        }

        if(expectedCount!=actualCount)
         return String.format("Total calls %1$s taken by Senior is greater than %2$s which are escalated by Junior", actualCount, expectedCount);
        return null;
    }
   private String verifyTotalCallsWithManager(CallCenterRequest req){
        int expectedCount=0;
        int actualCount=req.getMgr().size();
        for(List<Integer> seList:req.getSe()){
            for(Integer duration:seList){
                if(duration > Integer.parseInt(env.getProperty("senior.escalation.time")))
                    expectedCount++;
            }
        }
        if(expectedCount!=actualCount)
            return String.format("Total calls %1$s taken by Manager is greater than %2$s which are escalated by Senior", actualCount, expectedCount);

       return null;
    }
}
