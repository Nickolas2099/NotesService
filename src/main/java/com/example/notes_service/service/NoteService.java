package com.example.notes_service.service;

import com.example.notes_service.dto.NoteCreateRequestDto;
import com.example.notes_service.dto.NoteCreateResponseDto;
import com.example.notes_service.dto.NoteResponseDto;
import com.example.notes_service.dto.NoteUpdateRequestDto;
import com.example.notes_service.dto.NoteUpdateResponseDto;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.List;

public interface NoteService {

    @Nonnull
    NoteCreateResponseDto createNote(@Nonnull NoteCreateRequestDto noteCreateRequestDto);

    @Nullable
    List<NoteResponseDto> getAllNotes(String tag);

    @Nullable
    NoteResponseDto getNoteById(@Nonnull Long id);

    @Nonnull
    NoteUpdateResponseDto update(@Nonnull Long id, @Nonnull NoteUpdateRequestDto noteUpdateRequestDto);

    void delete(@Nonnull Long id);
}
