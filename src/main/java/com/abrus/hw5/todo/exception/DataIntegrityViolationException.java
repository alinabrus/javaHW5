package com.abrus.hw5.todo.exception;

public class DataIntegrityViolationException extends RuntimeException {
    public DataIntegrityViolationException(String entityType, String message) {
        super(entityType + " data integrity violation. " + message);
    }
}
