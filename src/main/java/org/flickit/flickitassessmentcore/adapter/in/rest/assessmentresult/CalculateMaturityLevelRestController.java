package org.flickit.flickitassessmentcore.adapter.in.rest.assessmentresult;

import lombok.RequiredArgsConstructor;
import org.flickit.flickitassessmentcore.application.port.in.assessmentresult.CalculateMaturityLevelUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class CalculateMaturityLevelRestController {

    private final CalculateMaturityLevelUseCase useCase;

    @PostMapping("{assessmentId}/assessment-result")
    public ResponseEntity<CalculateMaturityLevelResponseDto> calculateMaturityLevel(
        @PathVariable("assessmentId") UUID assessmentId) {
        CalculateMaturityLevelUseCase.Param result = new CalculateMaturityLevelUseCase.Param(assessmentId);
        CalculateMaturityLevelResponseDto responseDto =
            mapCommandToResponseDto(
                useCase.calculateMaturityLevel(result));
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    private CalculateMaturityLevelResponseDto mapCommandToResponseDto(CalculateMaturityLevelUseCase.Result result) {
        return new CalculateMaturityLevelResponseDto(
            result.assessmentResultId()
        );
    }

}
