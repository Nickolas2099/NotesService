package com.example.notes_service.exception.custom;

import com.example.notes_service.exception.ErrorCode;
import org.springframework.lang.NonNull;

public class NotFoundException extends BaseException {

    public NotFoundException(@NonNull String message) {
        super(ErrorCode.NOT_FOUND, message);
    }
}
