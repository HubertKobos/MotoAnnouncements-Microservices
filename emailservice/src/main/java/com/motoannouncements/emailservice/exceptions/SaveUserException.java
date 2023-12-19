package com.motoannouncements.emailservice.exceptions;

public class SaveUserException extends RuntimeException {
    public SaveUserException(String message){
        super(message);
    }
    public SaveUserException(String message, Throwable cause){
        super(message, cause);
    }
    public SaveUserException(Throwable cause){
        super(cause);
    }
}
