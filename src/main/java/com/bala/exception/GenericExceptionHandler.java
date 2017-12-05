package com.bala.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by hp on 12/3/2017
 */
@ControllerAdvice
public class GenericExceptionHandler {

    @Autowired
    private MessageSource messageSource;

       @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> invalidInput(MethodArgumentNotValidException ex) {
            BindingResult result = ex.getBindingResult();
           ErrorResponse response = new ErrorResponse();
            response.setErrorCode("Validation Error");
           response.setFieldName(result.getFieldError().getField());
             response.setErrorMessage("Invalid inputs:"+result.getFieldError().getDefaultMessage());
            return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
        }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> unExpectedException(Throwable ex) {
       // BindingResult result = ex.getBindingResult();
        ErrorResponse response = new ErrorResponse();
        response.setErrorCode("Internal Server Error");
        response.setErrorMessage("Unexpected Error:  "+ex.getMessage());
        return new ResponseEntity<ErrorResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    }

