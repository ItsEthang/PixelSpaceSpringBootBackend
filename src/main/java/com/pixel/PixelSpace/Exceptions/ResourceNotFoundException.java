package com.pixel.PixelSpace.Exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String error) {
        super(error);
    }
}
