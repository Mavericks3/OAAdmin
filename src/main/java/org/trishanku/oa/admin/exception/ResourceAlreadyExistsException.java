package org.trishanku.oa.admin.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends Exception{

    private static final long serialVersionUID = 1L;

    public ResourceAlreadyExistsException(String message){
        super(message);
    }
}

