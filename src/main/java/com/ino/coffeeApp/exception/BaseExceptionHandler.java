package com.ino.coffeeApp.exception;

import com.ino.coffeeApp.dto.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(ValidateRecordException.class)
    public ResponseEntity<Object> handleValidateRecordException(ValidateRecordException ex) {
        ExceptionResponseDto responseDto = new ExceptionResponseDto();
        responseDto.setMessage(ex.getMessage());
        responseDto.setCode(ex.getCode());

        return new ResponseEntity<>(responseDto, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
