package com.medcodeai.autocoder.repository;

import com.medcodeai.autocoder.dto.NoteResultJoinDto;

import java.util.List;

public interface MedicalNoteEmRepository {

    List<NoteResultJoinDto> getMedicalNotesForUser(final String username);
}
