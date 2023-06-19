package org.flickit.assessment.core.adapter.out.persistence.assessmentresult;

import lombok.RequiredArgsConstructor;
import org.flickit.assessment.core.adapter.out.persistence.assessmentresult.exception.AssessmentResultNotFound;
import org.flickit.assessment.core.application.port.out.assessmentresult.LoadAssessmentResultPort;
import org.flickit.assessment.core.application.port.out.assessmentresult.SaveAssessmentResultPort;
import org.flickit.assessment.core.application.port.out.assessmentresult.LoadAssessmentResultByAssessmentPort;
import org.flickit.assessment.core.domain.AssessmentResult;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class AssessmentResultPersistenceAdapter implements SaveAssessmentResultPort, LoadAssessmentResultPort, LoadAssessmentResultByAssessmentPort {

    private final AssessmentResultRepository assessmentResultRepository;

    @Override
    public AssessmentResult saveAssessmentResult(AssessmentResult assessmentResult) {
        return AssessmentResultMapper.mapToDomainModel(assessmentResultRepository.save(AssessmentResultMapper.mapToJpaEntity(assessmentResult)));
    }

    @Override
    public AssessmentResult loadAssessmentResult(UUID resultId) {
        return AssessmentResultMapper.mapToDomainModel(
            assessmentResultRepository.findById(resultId).orElseThrow(
                () -> new AssessmentResultNotFound("Assessment Result with id [" + resultId + "] not found!")));
    }

    @Override
    public Set<AssessmentResult> loadAssessmentResultByAssessmentId(UUID assessmentId) {
        return assessmentResultRepository.findAssessmentResultByAssessmentId(assessmentId).stream()
            .map(AssessmentResultMapper::mapToDomainModel)
            .collect(Collectors.toSet());
    }
}