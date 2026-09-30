package com.example.notesservice.exception;

import org.springframework.lang.NonNull;

public class NotFoundException extends BaseException {

    public NotFoundException(@NonNull String message) {
        super(ErrorCode.NOT_FOUND, message);
    }
}
