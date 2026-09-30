package com.example.notesservice.exception;

import jakarta.annotation.Nonnull;

public class AlreadyExistsException extends BaseException{

    public AlreadyExistsException(@Nonnull String message) {
        super(ErrorCode.BAD_REQUEST, message);
    }

}
