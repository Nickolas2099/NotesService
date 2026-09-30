package com.example.notesservice.repository;

import com.example.notesservice.model.Note;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByTagContainingIgnoreCase(String tag);
}
