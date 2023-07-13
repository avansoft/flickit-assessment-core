package org.flickit.flickitassessmentcore.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Questionnaire {
    private Long id;
    private String code;
    private String title;
    private String description;
    private LocalDateTime creationTime;
    private LocalDateTime lastModificationDate;
    private Integer index;
    private AssessmentKit assessmentKit;

    @Override
    public String toString() {
        return title;
    }
}
