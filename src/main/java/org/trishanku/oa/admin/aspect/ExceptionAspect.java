package org.trishanku.oa.admin.aspect;

import java.util.Date;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.trishanku.oa.admin.exception.OAException;
import org.trishanku.oa.admin.exception.ResourceAlreadyExistsException;

@ControllerAdvice
public class ExceptionAspect {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        OAException oaException = new OAException(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(oaException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<?> ResourceAlreadyExistsException(ResourceAlreadyExistsException ex, WebRequest request) {
        OAException oaException = new OAException(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(oaException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        OAException oaException = new OAException(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(oaException, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}