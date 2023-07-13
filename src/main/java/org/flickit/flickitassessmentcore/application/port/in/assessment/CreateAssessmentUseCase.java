package org.flickit.flickitassessmentcore.application.port.in.assessment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;
import org.flickit.flickitassessmentcore.common.SelfValidating;

import java.util.UUID;

import static org.flickit.flickitassessmentcore.common.ErrorMessageKey.*;

public interface CreateAssessmentUseCase {

    Result createAssessment(Param param);

    @Value
    class Param extends SelfValidating<Param> {

        @NotBlank(message = CREATE_ASSESSMENT_TITLE_NOT_BLANK)
        @Size(min = 3, message = CREATE_ASSESSMENT_TITLE_SIZE_MIN)
        @Size(max = 100, message = CREATE_ASSESSMENT_TITLE_SIZE_MAX)
        String title;

        @NotNull(message = CREATE_ASSESSMENT_ASSESSMENT_KIT_ID_NOT_NULL)
        Long assessmentKitId;

        Integer colorId;

        @NotNull(message = CREATE_ASSESSMENT_SPACE_ID_NOT_NULL)
        Long spaceId;

        public Param(Long spaceId, String title, Long assessmentKitId, Integer colorId) {
            this.title = title != null ? title.strip() : null;
            this.spaceId = spaceId;
            this.assessmentKitId = assessmentKitId;
            this.colorId = colorId;
            this.validateSelf();
        }
    }

    record Result(UUID id){
    }
}
