package org.flickit.assessment.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class QuestionImpact {
    private Long id;
    private Integer level;
    private MaturityLevel maturityLevel;
    private Question question;
    private QualityAttribute qualityAttribute;
    private Integer weight;
}