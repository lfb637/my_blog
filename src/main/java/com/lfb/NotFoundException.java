package com.lfb;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author lfb
 * @data 2022/8/18 9:41
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{
    public NotFoundException(){}
    public NotFoundException(String message) {
        super(message);
    }
    public NotFoundException(String message,Throwable t) {
        super(message,t);
    }
}
