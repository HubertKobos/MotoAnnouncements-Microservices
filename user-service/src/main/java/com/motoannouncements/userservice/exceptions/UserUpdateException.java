package com.motoannouncements.userservice.exceptions;

public class UserUpdateException extends RuntimeException{
    public UserUpdateException(String message){
        super(message);
    }
    public UserUpdateException(String message, Throwable cause){
        super(message, cause);
    }
    public UserUpdateException(Throwable cause){
        super(cause);
    }
}
