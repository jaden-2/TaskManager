package com.Task.Manager.customExceptions;

public class CredentialChangedException extends RuntimeException{
    public CredentialChangedException(String message){
        super(message);
    }
}
