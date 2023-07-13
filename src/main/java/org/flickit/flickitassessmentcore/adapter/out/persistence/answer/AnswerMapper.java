package org.flickit.flickitassessmentcore.adapter.out.persistence.answer;

import org.flickit.flickitassessmentcore.application.port.out.answer.SaveAnswerPort;
import org.flickit.flickitassessmentcore.domain.Answer;

public class AnswerMapper {

    public static AnswerJpaEntity mapSaveParamToJpaEntity(SaveAnswerPort.Param param) {
        return new AnswerJpaEntity(
            null,
            null,
            param.questionId(),
            param.answerOptionId()
        );
    }

    public static Answer mapToDomainModel(AnswerJpaEntity answerJpaEntity) {
        return new Answer(
            answerJpaEntity.getId(),
            answerJpaEntity.getAssessmentResult().getId(),
            answerJpaEntity.getQuestionId(),
            answerJpaEntity.getAnswerOptionId()
        );
    }
}
