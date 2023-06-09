package com.sosoburger.back.advice;

import com.sosoburger.back.exception.ErrorMessage;
import com.sosoburger.back.exception.NotFoundException;
import com.sosoburger.back.exception.UploadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdviceController {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> notFoundException(NotFoundException exception){
        return new ResponseEntity<>(new ErrorMessage(exception.getMessage()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UploadException.class)
    public ResponseEntity<ErrorMessage> uploadException(UploadException exception){
        return new ResponseEntity<>(new ErrorMessage(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
