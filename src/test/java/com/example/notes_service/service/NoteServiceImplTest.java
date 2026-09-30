package com.example.notes_service.service;

import com.example.notes_service.mapper.NoteBaseMapper;
import com.example.notes_service.dto.NoteCreateRequestDto;
import com.example.notes_service.dto.NoteCreateResponseDto;
import com.example.notes_service.dto.NoteResponseDto;
import com.example.notes_service.exception.custom.AlreadyExistsException;
import com.example.notes_service.model.Note;
import com.example.notes_service.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceImplTest {

    @Mock
    private NoteRepository noteRepository;
    @Mock
    private NoteBaseMapper noteBaseMapper;
    @InjectMocks
    private NoteServiceImpl noteService;

    @Test
    void shouldCreateNoteSuccessfully() {

        Long id = 1L;
        String title = "meal";
        String text = "milk,bread,tea";
        String tag = "buy";
        LocalDateTime createdAt = LocalDateTime.of(2026, 6, 3, 12, 0, 0);

        NoteCreateRequestDto requestDto = new NoteCreateRequestDto(title, text, tag);

        Note note = new Note();
        note.setTitle(title);
        note.setContent(text);
        note.setTag(tag);

        Note savedNote = new Note();
        savedNote.setId(id);
        savedNote.setTitle(title);
        savedNote.setContent(text);
        savedNote.setTag(tag);
        savedNote.setCreatedAt(createdAt);

        NoteCreateResponseDto expectedResponse = new NoteCreateResponseDto(id, title, createdAt, tag);

        when(noteBaseMapper.toNote(requestDto)).thenReturn(note);
        when(noteRepository.save(note)).thenReturn(savedNote);
        when(noteBaseMapper.toCreateNoteResponseDto(savedNote)).thenReturn(expectedResponse);

        NoteCreateResponseDto actualResult = noteService.createNote(requestDto);

        assertThat(actualResult).isNotNull().isEqualTo(expectedResponse);
        verify(noteRepository).save(note);
    }

    @Test
    void getByTag_ShouldReturnListOfNoteResponseDto_WhenNotesExist() {

        Long workId = 1L;
        String workTitle = "routineTask";
        String workContent = "add table in db, delete some information from site";
        String workTag = "work";
        LocalDateTime workCreatedAt = LocalDateTime.of(2026, 6, 3, 12, 0, 0);

        Long todayWorkId = 2L;
        String todayWorkTitle = "routineTask";
        String todayWorkContent = "add table in db, delete some information from site";
        String todayWorkTag = "work";
        LocalDateTime todayWorkCreatedAt = LocalDateTime.of(2026, 6, 3, 12, 20, 0);

        Note savedNoteWork = new Note();
        savedNoteWork.setId(workId);
        savedNoteWork.setTitle(workTitle);
        savedNoteWork.setContent(workContent);
        savedNoteWork.setTag(workTag);
        savedNoteWork.setCreatedAt(workCreatedAt);

        Note savedNoteWorkToday = new Note();
        savedNoteWorkToday.setId(todayWorkId);
        savedNoteWorkToday.setTitle(todayWorkTitle);
        savedNoteWorkToday.setContent(todayWorkContent);
        savedNoteWorkToday.setTag(todayWorkTag);
        savedNoteWorkToday.setCreatedAt(todayWorkCreatedAt);

        NoteResponseDto firstExpectedDto = new NoteResponseDto(workId, workTitle, workContent, workTag, workCreatedAt);
        NoteResponseDto secondExpectedDto = new NoteResponseDto(todayWorkId, todayWorkTitle, todayWorkContent, todayWorkTag, todayWorkCreatedAt);

        String searchTag = "wor";
        List<Note> mockNotesList = List.of(savedNoteWork, savedNoteWorkToday);

        when(noteRepository.findByTagContainingIgnoreCase(searchTag)).thenReturn(mockNotesList);

        when(noteBaseMapper.toNoteResponseDto(savedNoteWork)).thenReturn(firstExpectedDto);
        when(noteBaseMapper.toNoteResponseDto(savedNoteWorkToday)).thenReturn(secondExpectedDto);

        List<NoteResponseDto> actualResult = noteService.getAllNotes(searchTag);

        assertNotNull(actualResult);
        assertEquals(2, actualResult.size());

        NoteResponseDto firstDto = actualResult.get(0);
        assertEquals(workId, firstDto.id());
        assertEquals(workTitle, firstDto.title());
        assertEquals(workContent, firstDto.text());
        assertEquals(workCreatedAt, firstDto.createdAt());
        assertEquals(workTag, firstDto.tag());

        NoteResponseDto secondDto = actualResult.get(1);
        assertEquals(todayWorkId, secondDto.id());
        assertEquals(todayWorkTitle, secondDto.title());
        assertEquals(todayWorkContent, secondDto.text());
        assertEquals(todayWorkCreatedAt, secondDto.createdAt());
        assertEquals(todayWorkTag, secondDto.tag());

        verify(noteRepository).findByTagContainingIgnoreCase(searchTag);
        verify(noteBaseMapper).toNoteResponseDto(savedNoteWork);
        verify(noteBaseMapper).toNoteResponseDto(savedNoteWorkToday);

    }

    @Test
    void getByTag_ShouldReturnEmptyList_WhenNoNotesFound() {

        String searchTag = "NonExistingTag";

        when(noteRepository.findByTagContainingIgnoreCase(searchTag)).thenReturn(List.of());

        List<NoteResponseDto> actualResult = noteService.getAllNotes(searchTag);

        assertNotNull(actualResult);
        assertThat(actualResult).isEmpty();

        verify(noteRepository).findByTagContainingIgnoreCase(searchTag);
        verifyNoInteractions(noteBaseMapper);
    }

}