package com.example.notes_service.dto;

public record NoteUpdateRequestDto(
        String title,
        String text
) {
}
