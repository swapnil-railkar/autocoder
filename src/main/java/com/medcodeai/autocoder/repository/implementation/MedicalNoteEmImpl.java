package com.medcodeai.autocoder.repository.implementation;

import com.medcodeai.autocoder.dto.NoteResultJoinDto;
import com.medcodeai.autocoder.model.MedicalNote;
import com.medcodeai.autocoder.model.Result;
import com.medcodeai.autocoder.repository.MedicalNoteEmRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MedicalNoteEmImpl implements MedicalNoteEmRepository {

    private final EntityManager entityManager;

    @Override
    public List<NoteResultJoinDto> getMedicalNotesForUser(final String username) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<NoteResultJoinDto> criteriaQuery = criteriaBuilder.createQuery(NoteResultJoinDto.class);
        final Root<MedicalNote> medicalNoteRootroot = criteriaQuery.from(MedicalNote.class);
        final Root<Result> resultRoot = criteriaQuery.from(Result.class);

        criteriaQuery.multiselect(medicalNoteRootroot.get("fileName"),
                medicalNoteRootroot.get("extractedText"),
                resultRoot.get("resultJson"),
                resultRoot.get("processedAt"));
        criteriaQuery.where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(medicalNoteRootroot.get("username").get("username"), username),
                        criteriaBuilder.equal(medicalNoteRootroot.get("id"), resultRoot.get("medicalNote").get("id"))
                )
        );
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
