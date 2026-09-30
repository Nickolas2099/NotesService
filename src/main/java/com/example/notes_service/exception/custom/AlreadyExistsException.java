package com.example.notes_service.exception.custom;

import com.example.notes_service.exception.ErrorCode;
import jakarta.annotation.Nonnull;

public class AlreadyExistsException extends BaseException{

    public AlreadyExistsException(@Nonnull String message) {
        super(ErrorCode.BAD_REQUEST, message);
    }

}
