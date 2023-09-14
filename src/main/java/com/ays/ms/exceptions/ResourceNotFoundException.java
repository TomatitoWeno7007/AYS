package com.ays.ms.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {}

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
