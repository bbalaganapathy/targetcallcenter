package com.bala.validator;

import com.bala.model.CallCenterRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hp on 11/30/2017.
 */


public class IsJEGreaterThanSEAndMgrValidator implements ConstraintValidator<IsJEGreaterThanSEAndMgr, CallCenterRequest> {
    @Override
    public void initialize(IsJEGreaterThanSEAndMgr constraintAnnotation) {

    }

    @Override
    public boolean isValid(CallCenterRequest value, ConstraintValidatorContext context) {
        if (value == null) return true;
        if (value.getJe().length < value.getSe().length) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Number of Junior executives should be more than Senior executives")
                    .addPropertyNode("je").addConstraintViolation();
            return false;
        }

        if(!verifyCallsHandled(value,true)){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("One of the junior executive is handing more than allowed limit ")
                    .addPropertyNode("se").addConstraintViolation();
            return false;
        }
        if(!verifyCallsHandled(value,false)){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("One of the senior executive is handing more than allowed limit ")
                    .addPropertyNode("se").addConstraintViolation();
            return false;
        }
  /*    int totalCalls=totalCalls(value);
      if(totalCalls!=value.getNoOfCalls()){
          context.disableDefaultConstraintViolation();
          context.buildConstraintViolationWithTemplate("Total calls are not matching with number of calls")
                  .addPropertyNode("").addConstraintViolation();
          return false;
      }*/


        return true;
    }


    private int totalCalls(CallCenterRequest req){
        int totalCalls=0;
       // totalCalls+=req.getJe().length+req.getSe().length+req.getMgr().split(",").length;
        return  totalCalls;
    }

    private boolean verifyCallsHandled(CallCenterRequest req,boolean isJunior){
        int totalCalls = req.getNoOfCalls();

        int maxCalls=Math.abs(totalCalls/ (req.getJe().length+req.getSe().length));
        if(isJunior){
            for(String exe:req.getJe()){
                if(exe.split(",").length > maxCalls){
                    return false;
                }
            }
        }else{
            for(String exe:req.getSe()){
                if(exe.split(",").length > maxCalls){
                    return false;
                }
            }
        }

        return true;
    }
}
