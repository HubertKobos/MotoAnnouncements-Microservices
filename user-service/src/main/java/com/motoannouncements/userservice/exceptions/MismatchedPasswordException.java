package com.motoannouncements.userservice.exceptions;

public class MismatchedPasswordException extends RuntimeException{
    public MismatchedPasswordException(String message){
        super(message);
    }
    public MismatchedPasswordException(String message, Throwable cause){
        super(message, cause);
    }
    public MismatchedPasswordException(Throwable cause){
        super(cause);
    }

}
