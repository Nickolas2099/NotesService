package com.example.notesservice.dto;

public record NoteUpdateRequestDto(
        String title,
        String text
) {
}
