package com.example.notesservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorDto> handleBaseException(@NonNull BaseException exception,
                                                        @NonNull HttpServletRequest request) {

        final int statusCode = exception.getErrorCode().getCode();

        final ErrorDto errorDto = new ErrorDto(
                statusCode,
                exception.getMessage(),
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return ResponseEntity.status(statusCode).body(errorDto);
    }

}
