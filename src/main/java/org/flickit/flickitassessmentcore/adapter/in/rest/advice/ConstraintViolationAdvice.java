package org.flickit.flickitassessmentcore.adapter.in.rest.advice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.flickit.flickitassessmentcore.adapter.in.rest.advice.common.ValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static org.flickit.flickitassessmentcore.adapter.in.rest.advice.common.ErrorBundleLoader.getErrorMessage;

@RestControllerAdvice
public class ConstraintViolationAdvice {

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ValidationError> handleConstraintViolation(ConstraintViolationException ex) {
        return buildValidationErrors(ex.getConstraintViolations());
    }

    private List<ValidationError> buildValidationErrors(
        Set<ConstraintViolation<?>> violations) {
        if (violations != null)
            return violations.
                stream().
                map(violation ->
                    new ValidationError(
                        violation.getPropertyPath().toString(),
                        getErrorMessage(violation.getMessage()))).
                collect(toList());
        return null;
    }
}