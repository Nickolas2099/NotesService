package com.example.notesservice.exception;

import jakarta.annotation.Nonnull;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final ErrorCode errorCode;

    protected BaseException(@Nonnull ErrorCode errorCode, @Nonnull String message) {
        super(message);
        this.errorCode = errorCode;
    }

}
