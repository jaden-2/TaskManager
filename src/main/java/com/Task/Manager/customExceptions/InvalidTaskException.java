package com.Task.Manager.customExceptions;
/*
* Thrown when makes and invalid query to database example: Requesting for a task that does not exist*/
public class InvalidTaskException extends RuntimeException{
    public InvalidTaskException(String message){
        super(message);
    }
}
