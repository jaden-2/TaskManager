package com.Task.Manager.customExceptions;

import com.Task.Manager.entity.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthenticatedOperationException.class)
    public ResponseEntity<ErrorMessage> handleInvalidException(UnauthenticatedOperationException e){
        return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidTaskException.class)
    public ResponseEntity<ErrorMessage> handleInvalidTaskException(InvalidTaskException e){
        return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> handleRuntimeException(RuntimeException e){
        return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
