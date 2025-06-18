package com.medcodeai.autocoder.repository;

import com.medcodeai.autocoder.model.MedicalNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalNoteRepository extends JpaRepository<MedicalNote, Long> {
}
