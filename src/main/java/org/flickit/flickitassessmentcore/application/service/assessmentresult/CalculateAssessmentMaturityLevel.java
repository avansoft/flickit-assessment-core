package org.flickit.flickitassessmentcore.application.service.assessmentresult;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flickit.flickitassessmentcore.application.port.out.maturitylevel.LoadMaturityLevelByKitPort;
import org.flickit.flickitassessmentcore.application.service.exception.ResourceNotFoundException;
import org.flickit.flickitassessmentcore.common.ErrorMessageKey;
import org.flickit.flickitassessmentcore.domain.SubjectValue;
import org.flickit.flickitassessmentcore.domain.MaturityLevel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Transactional
@RequiredArgsConstructor
@Component
@Slf4j
public class CalculateAssessmentMaturityLevel {

    private final LoadMaturityLevelByKitPort loadMaturityLevelByKitPort;

    public MaturityLevel calculateAssessmentMaturityLevel(List<SubjectValue> subjectValues, Long assessmentKitId) {
        Set<MaturityLevel> maturityLevels = loadMaturityLevelByKitPort.loadMaturityLevelByKitId(new LoadMaturityLevelByKitPort.Param(assessmentKitId)).maturityLevels();
        long mean = calculateMeanOfSubjectMaturityLevels(subjectValues);
        return findMaturityLevelByValue(mean, maturityLevels);
    }

    private MaturityLevel findMaturityLevelByValue(long mean, Set<MaturityLevel> maturityLevels) {
        for (MaturityLevel ml : maturityLevels) {
            if (ml.getValue() == mean) {
                return ml;
            }
        }
        throw new ResourceNotFoundException(ErrorMessageKey.CALCULATE_MATURITY_LEVEL_MATURITY_LEVEL_NOT_FOUND_MESSAGE);
    }

    private long calculateMeanOfSubjectMaturityLevels(List<SubjectValue> subjectValues) {
        double sum = 0;
        for (SubjectValue subjectValue : subjectValues) {
            sum += subjectValue.getMaturityLevel().getValue();
        }
        return Math.round(sum / subjectValues.size());
    }
}
