package org.flickit.flickitassessmentcore.application.service.assessment;

import lombok.RequiredArgsConstructor;
import org.flickit.flickitassessmentcore.application.port.in.assessment.GetAssessmentListUseCase;
import org.flickit.flickitassessmentcore.application.port.out.assessment.LoadAssessmentsWithMaturityLevelIdBySpacePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class GetAssessmentListService implements GetAssessmentListUseCase {

    private final LoadAssessmentsWithMaturityLevelIdBySpacePort loadAssessmentsBySpace;

    @Override
    public GetAssessmentListUseCase.Result getAssessmentList(GetAssessmentListUseCase.Param param) {
        return new GetAssessmentListUseCase.Result(
            loadAssessmentsBySpace.loadAssessmentsWithLastResultMaturityLevelIdBySpaceId(
                param.getSpaceId(),
                param.getPage(),
                param.getSize()
            )
        );
    }
}
