package org.flickit.flickitassessmentcore.application.port.out.answer;

import org.flickit.flickitassessmentcore.domain.Answer;

import java.util.Set;
import java.util.UUID;

public interface LoadAnswersByResultPort {

    Set<Answer> loadAnswersByResultId(UUID resultId);
}
