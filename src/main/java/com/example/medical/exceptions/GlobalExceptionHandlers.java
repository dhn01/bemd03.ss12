package com.example.medical.exceptions;

import com.example.medical.utils.wrappers.ApiResponse;
import com.example.medical.utils.wrappers.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tools.jackson.databind.exc.UnrecognizedPropertyException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandlers {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ApiResponse.response(
                HttpStatus.BAD_REQUEST.name(),
                "Fail data",
                errors
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String detailMessage = "Dữ liệu Request không hợp lệ";

        if (ex.getCause() instanceof UnrecognizedPropertyException unrecognizedEx) {
            String fieldName = unrecognizedEx.getPropertyName();
            detailMessage = String.format("Client cố tình gửi dữ liệu cấm: '%s'", fieldName);
        }
        log.warn("[WARNING] {}", detailMessage);

        return ErrorResponse.errorResponse(HttpStatus.BAD_REQUEST.name(), detailMessage);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ErrorResponse.errorResponse(HttpStatus.NOT_FOUND.name(), ex.getMessage());
    }

    @ExceptionHandler(ResourceAlreadyRemoveException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceAlreadyRemoveException(ResourceAlreadyRemoveException ex) {
        return ErrorResponse.errorResponse(HttpStatus.NOT_FOUND.name(), ex.getMessage());
    }

    @ExceptionHandler(ResourceNotEnoughException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleResourceNotEnoughException(ResourceNotEnoughException ex) {
        return ErrorResponse.errorResponse(HttpStatus.BAD_REQUEST.name(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR.name(), e.getMessage())
                );
    }
}
