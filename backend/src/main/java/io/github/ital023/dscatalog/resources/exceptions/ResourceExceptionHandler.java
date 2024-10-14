package io.github.ital023.dscatalog.resources.exceptions;

import io.github.ital023.dscatalog.services.exceptions.DataBaseException;
import io.github.ital023.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandartError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;


        StandartError err =
                new StandartError(Instant.now()
                , status.value()
                , "Resource Not Found"
                , e.getMessage()
                , request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<StandartError> database(DataBaseException e, HttpServletRequest request){

        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandartError err =
                new StandartError(Instant.now()
                        , status.value()
                        , "Resource Not Found"
                        , e.getMessage()
                        , request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandartError> validation(MethodArgumentNotValidException e,
                                                                         HttpServletRequest request) {

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        StandartError err = new StandartError(Instant.now(),
                    status.value(),
                    "Validation error",
                    e.getMessage(),
                request.getRequestURI()
                );

        return ResponseEntity.status(status).body(err);
    }




}
