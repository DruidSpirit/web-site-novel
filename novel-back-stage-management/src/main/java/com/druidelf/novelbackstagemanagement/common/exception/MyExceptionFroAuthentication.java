package com.druidelf.novelbackstagemanagement.common.exception;
import com.druidelf.novelbackstagemanagement.enums.responseType.ResponseInterface;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.AuthenticationException;


@NoArgsConstructor
public class MyExceptionFroAuthentication extends AuthenticationException implements ResponseInterface {

    private Integer statusCode;
    private String message;

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public MyExceptionFroAuthentication(ResponseInterface responseInterface) {
        this.statusCode = responseInterface.getStatusCode();
        this.message = responseInterface.getName();
    }

    public MyExceptionFroAuthentication(ResponseInterface responseInterface, Throwable e ) {
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
