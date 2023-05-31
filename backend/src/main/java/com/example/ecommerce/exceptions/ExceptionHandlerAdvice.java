package com.example.ecommerce.exceptions;

import com.example.ecommerce.utils.ResponseUtil;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
public class ExceptionHandlerAdvice {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseUtil> conflict(DataIntegrityViolationException ex){
        String message = getMostSpecificMessage(ex);

        return new ResponseEntity<ResponseUtil>(new ResponseUtil(false, message), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ResponseUtil> validationException(ValidationException ex){
        String message = ex.getMessage();

        return new ResponseEntity<ResponseUtil>(new ResponseUtil(false, message), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseUtil> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){
        ex.printStackTrace();

        String message = ex.getMessage();
        return new ResponseEntity<ResponseUtil>(new ResponseUtil(false, message), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseUtil> unhandledExceptions(Exception ex){
        String message = NestedExceptionUtils.getMostSpecificCause(ex).getMessage();

        ex.printStackTrace();

        return new ResponseEntity<ResponseUtil>(new ResponseUtil(false, message), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getMostSpecificMessage(DataIntegrityViolationException ex) {
        String message = NestedExceptionUtils.getMostSpecificCause(ex).getMessage();

        if(message.contains("Detail:")) {
            message = message.substring(message.indexOf("Detail:")+"Detail:".length());
        }

        return message;
    }
}
