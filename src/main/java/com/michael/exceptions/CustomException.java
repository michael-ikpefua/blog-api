package com.michael.exceptions;

import com.michael.utils.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class CustomException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleAnyException(Exception exception, WebRequest webRequest) {

        return new ResponseEntity<>(exception, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(value = {UserException.class, PostException.class, CommentException.class, LikePostException.class, LikeCommentException.class})
    public ResponseEntity<Object> handleCustomException(UserException ex, WebRequest webRequest) {
        String message = ex.getLocalizedMessage();
        if (message == null) message = ex.toString();
        ApiError apiError = new ApiError(new Date(), message, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }



//    @ExceptionHandler(value = {PostException.class, CommentException.class})
//    public ResponseEntity<Object> handlePostException(Exception exception, WebRequest webRequest) {
//
//        String message = exception.getLocalizedMessage();
//        if (message == null) message = exception.toString();
//
//        ApiError apiError = new ApiError(new Date(), message);
//        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
//
//    }
//
//    @ExceptionHandler(value = LikePostException.class)
//    public ResponseEntity<Object> handleLikePostException(LikePostException exception, WebRequest webRequest) {
//
//        String errorMessage = exception.getLocalizedMessage();
//        if (errorMessage == null) errorMessage = errorMessage.toString();
//
//        ApiError apiError = new ApiError(new Date(), errorMessage);
//
//        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
//
//    }

}
