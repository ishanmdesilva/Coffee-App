package com.ino.coffeeApp.exception;

public class ValidateRecordException extends RuntimeException {

    private String code;

    public ValidateRecordException(){}

    public ValidateRecordException(String message){
        super(message);
    }

    public ValidateRecordException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
