package com.example.notesservice.controller;

import com.example.notesservice.dto.NoteCreateRequestDto;
import com.example.notesservice.dto.NoteCreateResponseDto;
import com.example.notesservice.dto.NoteResponseDto;
import com.example.notesservice.dto.NoteUpdateRequestDto;
import com.example.notesservice.dto.NoteUpdateResponseDto;
import com.example.notesservice.service.NoteService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteCreateResponseDto> createNote(@RequestBody NoteCreateRequestDto noteCreateRequestDto) {
        NoteCreateResponseDto response = noteService.createNote(noteCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<NoteResponseDto>> getAllNotes(@RequestParam(name = "tag", required = false) String tag) {
        List<NoteResponseDto> response = noteService.getAllNotes(tag);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<NoteResponseDto> getNoteById(@PathVariable Long noteId) {
        NoteResponseDto response = noteService.getNoteById(noteId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<NoteUpdateResponseDto> updateNote(@PathVariable Long noteId,
                                                            @RequestBody NoteUpdateRequestDto noteUpdateRequestDto) {
        NoteUpdateResponseDto response = noteService.update(noteId, noteUpdateRequestDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long noteId) {
        noteService.delete(noteId);
        return ResponseEntity.ok().build();
    }
}
