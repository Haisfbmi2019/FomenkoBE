package com.anton.fomenko.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;

@Slf4j
@ControllerAdvice
public class ExcHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> constraintViolationExceptionException(ConstraintViolationException e) {
        var response = Response.builder()
                .code(400)
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException e) {
        var response = Response.builder()
                .code(401)
                .message("Token is incorrect")
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InternalViolationException.class)
    public ResponseEntity<?> handleInternalViolationException(InternalViolationException e) {
        var type = e.getType();
        var response = Response.builder()
                .code(type.getCode())
                .message(e.getCustomMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<?> handleException(BindException ex) {
        HttpStatus errorStatus = HttpStatus.BAD_REQUEST;

        ObjectError error = ex.getBindingResult().getAllErrors().get(0);
        Object[] arguments = error.getArguments();
        assert arguments != null;
        DefaultMessageSourceResolvable field = (DefaultMessageSourceResolvable) arguments[0];

        var response = MethodArgumentResponse.builder()
                .code(409)
                .field(field.getCode())
                .message(error.getDefaultMessage())
                .build();

        return new ResponseEntity<>(response, errorStatus);
    }

    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            IllegalStateException.class,
            HttpMessageNotReadableException.class,
            ClientAbortException.class
    })
    public ResponseEntity<?> handleRequestValidation(Exception e) {
        var response = InternalServerErrorResponse.builder()
                .exceptionClass(e.getClass().toString())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        var response = InternalServerErrorResponse.builder()
                .exceptionClass(e.getClass().toString())
                .message(e.getMessage())
                .trace(Arrays.toString(e.getStackTrace()))
                .build();
        e.printStackTrace();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Data
    @Builder
    private static class Response {
        private int code;
        private String message;
    }

    @Data
    @Builder
    private static class MethodArgumentResponse {
        private int code;
        private String field;
        private String message;
    }

    @Data
    @Builder
    private static class InternalServerErrorResponse {
        private String exceptionClass;
        private String message;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String trace;
    }

}
