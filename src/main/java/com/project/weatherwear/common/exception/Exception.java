package com.project.weatherwear.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class Exception extends RuntimeException{

    private final HttpStatus httpStatus;
    private final ErrorCode errorCode;
    private final String errorMessage;

    public Exception(ErrorCode errorCode){

        this.errorCode = errorCode;
        this.errorMessage = errorCode.getMessage();
        this.httpStatus = errorCode.getHttpStatus();
    }
}
