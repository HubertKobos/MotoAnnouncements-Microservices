package com.motoannouncements.emailservice.exceptions;

public class SaveAnnouncementException extends RuntimeException {
    public SaveAnnouncementException(String message){
        super(message);
    }
    public SaveAnnouncementException(String message, Throwable cause){
        super(message, cause);
    }
    public SaveAnnouncementException(Throwable cause){
        super(cause);
    }
}
