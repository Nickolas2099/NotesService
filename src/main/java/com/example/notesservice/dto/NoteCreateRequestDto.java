package com.example.notesservice.dto;

public record NoteCreateRequestDto(
        String title,
        String text,
        String tag
) {
}
