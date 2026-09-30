package com.example.notes_service.exception.custom;

import com.example.notes_service.exception.ErrorCode;
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
