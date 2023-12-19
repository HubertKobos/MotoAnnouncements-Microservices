package com.motoannouncements.userservice.exceptions;

public class AwsS3BucketException extends RuntimeException {
    public AwsS3BucketException(String message) {
        super(message);
    }
    public AwsS3BucketException(String message, Throwable cause){super(message, cause);}
    public AwsS3BucketException(Throwable cause){super(cause);}
}
