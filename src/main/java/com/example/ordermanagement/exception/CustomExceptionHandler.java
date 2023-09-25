package com.example.ordermanagement.exception;

import com.example.ordermanagement.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = CustomException.class)
    public @ResponseBody ResponseEntity<ErrorResponse> handleException(CustomException exception) {
        System.out.println("here");
        log.error(exception.getStatusCode() + ":" + exception.getMessage());
        return ResponseEntity.status(exception.getStatusCode())
                .body(new ErrorResponse(
                    exception.getStatusCode().value(), exception.getMessage()));
    }

//    @ExceptionHandler(value = IllegalArgumentException.class)
//    public @ResponseBody ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
//        log.error(exception.getMessage());
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
//    }
}

