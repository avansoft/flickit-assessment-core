package org.flickit.flickitassessmentcore.adapter.in.rest.exception;

import org.flickit.flickitassessmentcore.adapter.in.rest.exception.api.ErrorResponseDto;
import org.flickit.flickitassessmentcore.application.service.exception.ResourceNotFoundException;
import org.flickit.flickitassessmentcore.common.MessageBundle;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.flickit.flickitassessmentcore.adapter.in.rest.exception.api.ErrorCodes.NOT_FOUND;

@RestControllerAdvice
public class ResourceNotFoundExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponseDto handle(ResourceNotFoundException ex) {
        return new ErrorResponseDto(NOT_FOUND, MessageBundle.message(ex.getMessage()));
    }
}
