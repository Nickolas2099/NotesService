package com.example.notes_service.dto;

public record NoteCreateRequestDto(
        String title,
        String text,
        String tag
) {
}
