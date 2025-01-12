package com.abrus.hw5.todo.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entityType, Long id) {
        super(entityType + " object with id " + id + " is not found");
    }
}
