package com.bernard.ppmtool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> projectIdExceptionResponseHandler(ProjectIdExceptionHandler ex, WebRequest request){
       ProjectIdExceptionResponse projectIdExceptionResponse = new ProjectIdExceptionResponse(ex.getMessage());
       return new ResponseEntity<>(projectIdExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> projectNotFoundResponseHandler (ProjectNotFoundExceptionHandler ex, WebRequest request){
        ProjectNotFoundExceptionResponse projectNotFoundExceptionResponse = new ProjectNotFoundExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(projectNotFoundExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> userAlreadyExistResponseHandler (UserAlreadyExistExceptionHandler ex, WebRequest request){
        UserAlreadyExistResponse userAlreadyExistResponse = new UserAlreadyExistResponse(ex.getMessage());
        return new ResponseEntity<>(userAlreadyExistResponse, HttpStatus.BAD_REQUEST);
    }
 }
