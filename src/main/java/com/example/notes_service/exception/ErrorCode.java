package com.example.notes_service.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    CREATED(201, "Resource created"),
    NOT_FOUND(404, "Note with %s: %s not found"),
    BAD_REQUEST(400, "Bad Request");

    private final int code;
    private final String messageTemplate;

    ErrorCode(int code, String messageTemplate) {
        this.code = code;
        this.messageTemplate = messageTemplate;
    }

    public String formatMessage(Object... args) {
        return String.format(this.messageTemplate, args);
    }
}
