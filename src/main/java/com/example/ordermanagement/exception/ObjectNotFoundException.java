package com.example.ordermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends CustomException {
    public ObjectNotFoundException(Class entity) {
        super(HttpStatus.NOT_FOUND, entity.getSimpleName() + " not found");
    }
}