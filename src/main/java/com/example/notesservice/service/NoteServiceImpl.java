package com.example.notesservice.service;

import com.example.notesservice.dto.NoteCreateRequestDto;
import com.example.notesservice.dto.NoteCreateResponseDto;
import com.example.notesservice.dto.NoteResponseDto;
import com.example.notesservice.dto.NoteUpdateRequestDto;
import com.example.notesservice.dto.NoteUpdateResponseDto;
import com.example.notesservice.exception.AlreadyExistsException;
import com.example.notesservice.exception.ErrorCode;
import com.example.notesservice.exception.NotFoundException;
import com.example.notesservice.mapper.NoteBaseMapper;
import com.example.notesservice.model.Note;
import com.example.notesservice.repository.NoteRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final NoteBaseMapper noteBaseMapper;

    @Transactional
    @Override
    public NoteCreateResponseDto createNote(NoteCreateRequestDto noteCreateRequestDto) {
        final Note note = noteBaseMapper.toNote(noteCreateRequestDto);
        final NoteCreateResponseDto noteCreateResponseDto =
                noteBaseMapper.toCreateNoteResponseDto(noteRepository.save(note));
        log.info("Note: {} has been created", note.getId());
        return noteCreateResponseDto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<NoteResponseDto> getAllNotes(String tag) {
        log.info("TAG: {}", tag);
        if(tag == null || tag.isBlank()) {
            final List<Note> notes = noteRepository.findAll();
            log.debug("Notes id is: {}", notes.stream().map(Note::getId).toList());
            return noteBaseMapper.toNoteListResponseDto(notes);
        }
        return getByTag(tag);
    }

    private List<NoteResponseDto> getByTag(String tag) {
        final List<Note> notes = noteRepository.findByTagContainingIgnoreCase(tag);
        log.debug("Notes found: {}", notes.size());
        return notes.stream()
                .map(noteBaseMapper::toNoteResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public NoteResponseDto getNoteById(Long id) {
        final NoteResponseDto responseDto = noteRepository.findById(id)
                .map(noteBaseMapper::toNoteResponseDto)
                .orElseThrow(() -> new AlreadyExistsException(ErrorCode.BAD_REQUEST.formatMessage(id)));
        log.debug("Found note: {}", responseDto);
        return responseDto;
    }

    @Transactional
    @Override
    public NoteUpdateResponseDto update(Long id, NoteUpdateRequestDto noteUpdateRequestDto) {
        final Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND.formatMessage("id", id)));
        if (note.getTitle() != null) {
            note.setTitle(noteUpdateRequestDto.title());
        }
        if (note.getContent() != null) {
            note.setContent(noteUpdateRequestDto.text());
        }
        log.info("Updated note: {}", note.getId());
        return noteBaseMapper.toUpdateNoteResponseDto(noteRepository.save(note));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        final Note note = noteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Note with id: {} not found", id);
                    return new NotFoundException(ErrorCode.NOT_FOUND.formatMessage("id", id));
                });
        noteRepository.delete(note);
        log.info("Note: {} has been deleted", id);
    }
}
