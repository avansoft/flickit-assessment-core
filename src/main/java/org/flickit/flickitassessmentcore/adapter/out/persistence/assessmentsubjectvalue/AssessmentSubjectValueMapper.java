package org.flickit.flickitassessmentcore.adapter.out.persistence.assessmentsubjectvalue;

import org.flickit.flickitassessmentcore.adapter.out.persistence.assessmentresult.AssessmentResultMapper;
import org.flickit.flickitassessmentcore.adapter.out.persistence.assessmentsubjectvalue.AssessmentSubjectValueJpaEntity;
import org.flickit.flickitassessmentcore.adapter.out.persistence.qualityattributevalue.QualityAttributeValueJpaEntity;
import org.flickit.flickitassessmentcore.domain.*;

public class AssessmentSubjectValueMapper {

    public static AssessmentSubjectValue mapToDomainModel(AssessmentSubjectValueJpaEntity qualityAttributeValueEntity) {
        return new AssessmentSubjectValue(
            qualityAttributeValueEntity.getId(),
            new AssessmentSubject(qualityAttributeValueEntity.getAssessmentSubjectId()),
            new MaturityLevel(qualityAttributeValueEntity.getMaturityLevelId())
        );
    }

    public static AssessmentSubjectValueJpaEntity mapToJpaEntity(AssessmentSubjectValue qualityAttributeValue) {
        return new AssessmentSubjectValueJpaEntity(
            qualityAttributeValue.getId(),
            null, // TODO
            qualityAttributeValue.getAssessmentSubject().getId(),
            qualityAttributeValue.getMaturityLevel().getId()
        );
    }


}