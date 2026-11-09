package com.example.medical.exceptions;

public class ResourceAlreadyRemoveException extends RuntimeException {
    public ResourceAlreadyRemoveException(String message) {
        super(message);
    }
}
