package org.flickit.flickitassessmentcore.adapter.in.rest.exception;

import jakarta.validation.ConstraintViolationException;
import org.flickit.flickitassessmentcore.adapter.in.rest.exception.api.ErrorResponseDto;
import org.flickit.flickitassessmentcore.common.MessageBundle;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.flickit.flickitassessmentcore.adapter.in.rest.exception.api.ErrorCodes.INVALID_INPUT;

@RestControllerAdvice
public class Jsr303ValidationExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handle(ConstraintViolationException ex) {
        if (ex.getConstraintViolations() != null) {
            String message = ex.getConstraintViolations().iterator().next().getMessage();
            return new ErrorResponseDto(INVALID_INPUT, MessageBundle.message(message));
        }
        return new ErrorResponseDto(INVALID_INPUT, null);
    }
}
