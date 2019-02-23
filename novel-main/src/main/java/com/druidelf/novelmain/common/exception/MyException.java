package com.druidelf.novelmain.common.exception;

import com.druidelf.novelmain.enums.responseType.ResponseInterface;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class MyException extends Exception implements ResponseInterface {

    private Integer statusCode;
    private String message;

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public MyException(ResponseInterface responseInterface) {
        this.statusCode = responseInterface.getStatusCode();
        this.message = responseInterface.getName();
    }

    public MyException(ResponseInterface responseInterface, Throwable e ) {
        super(e);
        this.statusCode = responseInterface.getStatusCode();
        this.message = responseInterface.getName();
    }

    @Override
    public Integer getStatusCode() {
        return this.statusCode;
    }

    @Override
    public String getName() {
        return this.message;
    }
}
