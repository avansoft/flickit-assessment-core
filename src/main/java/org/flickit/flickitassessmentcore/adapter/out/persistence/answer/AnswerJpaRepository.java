package org.flickit.flickitassessmentcore.adapter.out.persistence.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnswerJpaRepository extends JpaRepository<AnswerJpaEntity, UUID> {

    Optional<AnswerIdAndOptionIdView> findByAssessmentResultIdAndQuestionId(UUID assessmentResultId, Long questionId);

    @Modifying
    @Query("UPDATE AnswerJpaEntity a SET a.answerOptionId=:answerOptionId WHERE a.id=:id")
    void updateAnswerOptionById(UUID id, Long answerOptionId);
}
