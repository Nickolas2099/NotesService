package com.example.notesservice.service;

import com.example.notesservice.dto.NoteCreateRequestDto;
import com.example.notesservice.dto.NoteCreateResponseDto;
import com.example.notesservice.dto.NoteResponseDto;
import com.example.notesservice.dto.NoteUpdateRequestDto;
import com.example.notesservice.dto.NoteUpdateResponseDto;

import java.util.List;

public interface NoteService {

    NoteCreateResponseDto createNote(NoteCreateRequestDto noteCreateRequestDto);

    List<NoteResponseDto> getAllNotes(String tag);

    NoteResponseDto getNoteById(Long id);

    NoteUpdateResponseDto update(Long id, NoteUpdateRequestDto noteUpdateRequestDto);

    void delete(Long id);
}
