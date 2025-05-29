package com.medcodeai.autocoder.exception;

import com.medcodeai.autocoder.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TextExtractionException.class)
    public ResponseEntity<Object> handleTextExtractionException(TextExtractionException exception) {
        return new ResponseEntity<>(ExceptionDto.builder()
                .code(500)
                .message(exception.getMessage())
                .timeStamp(LocalDateTime.now())
                .build(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException exception) {
        return new ResponseEntity<>(ExceptionDto.builder()
                .code(500)
                .message(exception.getMessage())
                .timeStamp(LocalDateTime.now())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
