package com.example.notes_service.service;

import com.example.notes_service.dto.NoteCreateRequestDto;
import com.example.notes_service.dto.NoteCreateResponseDto;
import com.example.notes_service.dto.NoteResponseDto;
import com.example.notes_service.dto.NoteUpdateRequestDto;
import com.example.notes_service.dto.NoteUpdateResponseDto;
import com.example.notes_service.exception.custom.AlreadyExistsException;
import com.example.notes_service.exception.ErrorCode;
import com.example.notes_service.exception.custom.NotFoundException;
import com.example.notes_service.mapper.NoteBaseMapper;
import com.example.notes_service.model.Note;
import com.example.notes_service.repository.NoteRepository;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
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
    public @Nonnull NoteCreateResponseDto createNote(@NonNull NoteCreateRequestDto noteCreateRequestDto) {
        final Note note = noteBaseMapper.toNote(noteCreateRequestDto);
        final NoteCreateResponseDto noteCreateResponseDto =
                noteBaseMapper.toCreateNoteResponseDto(noteRepository.save(note));
        log.info("Note: {} has been created", note.getId());
        return noteCreateResponseDto;
    }

    @Transactional(readOnly = true)
    @Override
    public @Nullable List<NoteResponseDto> getAllNotes(String tag) {
        log.info("TAG: {}", tag);
        if(tag == null || tag.isBlank()) {
            final List<Note> notes = noteRepository.findAll();
            log.debug("Notes id is: {}", notes.stream().map(Note::getId).toList());
            return noteBaseMapper.toNoteListResponseDto(notes);
        }
        return getByTag(tag);
    }

    private @Nonnull List<NoteResponseDto> getByTag(@Nonnull String tag) {
        final List<Note> notes = noteRepository.findByTagContainingIgnoreCase(tag);
        log.debug("Notes found: {}", notes.size());
        return notes.stream()
                .map(noteBaseMapper::toNoteResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public @Nullable NoteResponseDto getNoteById(@NonNull Long id) {
        final NoteResponseDto responseDto = noteRepository.findById(id)
                .map(noteBaseMapper::toNoteResponseDto)
                .orElseThrow(() -> new AlreadyExistsException(ErrorCode.BAD_REQUEST.formatMessage(id)));
        log.debug("Found note: {}", responseDto);
        return responseDto;
    }

    @Transactional
    @Override
    public @Nonnull NoteUpdateResponseDto update(@NonNull Long id, @Nonnull NoteUpdateRequestDto noteUpdateRequestDto) {
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
    public void delete(@NonNull Long id) {
        final Note note = noteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Note with id: {} not found", id);
                    return new NotFoundException(ErrorCode.NOT_FOUND.formatMessage("id", id));
                });
        noteRepository.delete(note);
        log.info("Note: {} has been deleted", id);
    }
}
