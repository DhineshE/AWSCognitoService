package com.cognito.exception;

/**
 * @author 921452
 *
 */
public class C2CCognitoServiceException extends Exception{

    private static final long serialVersionUID = -8492860907207555852L;
    private final String errorCode;

    public C2CCognitoServiceException(String errorCode, String message,
                    Throwable throwable){
        super(message,throwable);
        this.errorCode = errorCode;
    }
}
