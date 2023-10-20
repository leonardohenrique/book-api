package com.example.bookapi.error;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private final ModelMapper modelMapper;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, List<String>> errors = ex.getBindingResult().getFieldErrors().stream().collect(Collectors.groupingBy(FieldError::getField, Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));

        ex.getBindingResult().getGlobalErrors().forEach(error -> errors.computeIfAbsent(error.getObjectName(), key -> new ArrayList<>()).add(error.getDefaultMessage()));

        ProblemDetail body = ex.updateAndGetBody(this.getMessageSource(), LocaleContextHolder.getLocale());

        ValidationProblemDetail bodyWithValidation = modelMapper.map(body, ValidationProblemDetail.class);
        bodyWithValidation.setErrors(errors);

        return this.handleExceptionInternal(ex, bodyWithValidation, headers, status, request);
    }
}