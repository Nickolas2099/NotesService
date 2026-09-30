package com.example.notes_service.mapper;

import com.example.notes_service.dto.NoteCreateRequestDto;
import com.example.notes_service.dto.NoteCreateResponseDto;
import com.example.notes_service.dto.NoteResponseDto;
import com.example.notes_service.dto.NoteUpdateResponseDto;
import com.example.notes_service.model.Note;

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
