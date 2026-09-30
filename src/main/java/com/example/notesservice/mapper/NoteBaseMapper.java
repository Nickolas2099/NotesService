package com.example.notesservice.mapper;

import com.example.notesservice.dto.NoteCreateRequestDto;
import com.example.notesservice.dto.NoteCreateResponseDto;
import com.example.notesservice.dto.NoteResponseDto;
import com.example.notesservice.dto.NoteUpdateResponseDto;
import com.example.notesservice.model.Note;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NoteBaseMapper {

    NoteCreateResponseDto toCreateNoteResponseDto(Note note);

    @Mapping(target = "content", source = "text")
    Note toNote(NoteCreateRequestDto noteCreateRequestDto);

    @Mapping(target = "text", source = "content")
    NoteResponseDto toNoteResponseDto(Note note);

    @Mapping(target = "text", source = "content")
    NoteUpdateResponseDto toUpdateNoteResponseDto(Note note);

    List<NoteResponseDto> toNoteListResponseDto(List<Note> note);
}
