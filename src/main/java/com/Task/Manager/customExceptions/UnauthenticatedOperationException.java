package com.Task.Manager.customExceptions;
/*
* This is thrown when unauthorised operations are requested*/
public class UnauthenticatedOperationException extends RuntimeException{

    public UnauthenticatedOperationException(String message){
        super(message);
    }
}
