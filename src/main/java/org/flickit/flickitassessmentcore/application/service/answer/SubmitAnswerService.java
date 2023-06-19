package org.flickit.flickitassessmentcore.application.service.answer;

import lombok.RequiredArgsConstructor;
import org.flickit.flickitassessmentcore.application.port.in.answer.SubmitAnswerCommand;
import org.flickit.flickitassessmentcore.application.port.in.answer.SubmitAnswerUseCase;
import org.flickit.flickitassessmentcore.application.port.out.answer.CheckAnswerExistenceByAssessmentResultIdAndQuestionIdPort;
import org.flickit.flickitassessmentcore.application.port.out.answer.LoadAnswerByAssessmentResultIdAndQuestionIdPort;
import org.flickit.flickitassessmentcore.application.port.out.answer.SaveAnswerPort;
import org.flickit.flickitassessmentcore.application.port.out.answer.UpdateAnswerPort;
import org.flickit.flickitassessmentcore.application.port.out.assessmentresult.CheckAssessmentResultExistencePort;
import org.flickit.flickitassessmentcore.application.port.out.assessmentresult.InvalidateAssessmentResultPort;
import org.flickit.flickitassessmentcore.application.service.exception.ResourceNotFoundException;
import org.flickit.flickitassessmentcore.domain.Answer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

import static org.flickit.flickitassessmentcore.common.ErrorMessageKey.SUBMIT_ANSWER_ASSESSMENT_RESULT_ID_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
@Transactional
public class SubmitAnswerService implements SubmitAnswerUseCase {

    private final SaveAnswerPort saveAnswerPort;
    private final UpdateAnswerPort updateAnswerPort;
    private final LoadAnswerByAssessmentResultIdAndQuestionIdPort loadAnswerPort;
    private final InvalidateAssessmentResultPort invalidateAssessmentResultPort;
    private final CheckAssessmentResultExistencePort assessmentResultExistencePort;
    private final CheckAnswerExistenceByAssessmentResultIdAndQuestionIdPort checkAnswerExistencePort;

    @Override
    public UUID submitAnswer(SubmitAnswerCommand command) {
        validateCommand(command);
        SaveOrUpdateResponse response = saveOrUpdate(command);
        afterSave(command, response);
        return response.answerId();
    }

    private void validateCommand(SubmitAnswerCommand command) {
        checkAssessmentResultExistence(command.getAssessmentResultId());
    }

    private SaveOrUpdateResponse saveOrUpdate(SubmitAnswerCommand command) {
        boolean exists = checkAnswerExistencePort.existsByAssessmentResultIdAndQuestionId(command.getAssessmentResultId(), command.getQuestionId());
        if (exists) {
            return update(command);
        }
        return new SaveOrUpdateResponse(true, save(command));
    }

    private void afterSave(SubmitAnswerCommand command, SaveOrUpdateResponse response) {
        if (response.hasChanged())
            invalidateAssessmentResultPort.invalidateAssessmentResultById(command.getAssessmentResultId());
    }

    private UUID save(SubmitAnswerCommand command) {
        return saveAnswerPort.persist(toSaveParam(command));
    }

    private SaveOrUpdateResponse update(SubmitAnswerCommand command) {
        Answer answer = loadAnswerPort.loadByAssessmentResultIdAndQuestionId(command.getAssessmentResultId(), command.getQuestionId());
        if (answerHasChanged(command, answer))
            return new SaveOrUpdateResponse(true, updateAnswerPort.update(toUpdateParam(answer.getId(), command)));
        return new SaveOrUpdateResponse(false, answer.getId());
    }

    private boolean answerHasChanged(SubmitAnswerCommand command, Answer answer) {
        return !Objects.equals(command.getAnswerOptionId(), answer.getAnswerOptionId());
    }

    private void checkAssessmentResultExistence(UUID assessmentResultId) {
        if (!assessmentResultExistencePort.existsById(assessmentResultId))
            throw new ResourceNotFoundException(SUBMIT_ANSWER_ASSESSMENT_RESULT_ID_NOT_FOUND_MESSAGE);
    }

    private SaveAnswerPort.Param toSaveParam(SubmitAnswerCommand command) {
        return new SaveAnswerPort.Param(
            command.getAssessmentResultId(),
            command.getQuestionId(),
            command.getAnswerOptionId()
        );
    }

    private UpdateAnswerPort.Param toUpdateParam(UUID id, SubmitAnswerCommand command) {
        return new UpdateAnswerPort.Param(
            id,
            command.getAssessmentResultId(),
            command.getQuestionId(),
            command.getAnswerOptionId()
        );
    }

    record SaveOrUpdateResponse(boolean hasChanged, UUID answerId) {
    }
}