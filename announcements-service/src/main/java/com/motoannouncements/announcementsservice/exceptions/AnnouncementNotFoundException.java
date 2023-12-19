package com.motoannouncements.announcementsservice.exceptions;

public class AnnouncementNotFoundException extends RuntimeException{
    public AnnouncementNotFoundException(String message) {
        super(message);
    }

}
