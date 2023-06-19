package org.flickit.assessment.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AssessmentResult {
    private UUID id;
    private Assessment assessment;
    private List<QualityAttributeValue> qualityAttributeValues;
    private List<AssessmentSubjectValue> assessmentSubjectValues;
    private boolean isValid;

    @Override
    public String toString() {
        return id.toString();
    }
}