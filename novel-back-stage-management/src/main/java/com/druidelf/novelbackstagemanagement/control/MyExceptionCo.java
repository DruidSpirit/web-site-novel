package com.druidelf.novelbackstagemanagement.control;


import com.druidelf.novelbackstagemanagement.common.exception.MyException;
import com.druidelf.novelbackstagemanagement.common.exception.MyExceptionFroAuthentication;
import com.druidelf.novelbackstagemanagement.enums.responseType.ResponseInterface;
import com.druidelf.novelbackstagemanagement.response.ResponseData;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

import static com.druidelf.novelbackstagemanagement.enums.responseType.ShiroExceptionType.*;

@SuppressWarnings({"unchecked", "JavaDoc"})
@ControllerAdvice
public class MyExceptionCo {

    /**
     * 参数校验异常
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler( MethodArgumentNotValidException.class )
    public ResponseData errorHandler(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        Map map = new HashMap();
        for (FieldError fieldError:bindingResult.getFieldErrors()) {
            map.put( fieldError.getDefaultMessage(),fieldError.getField() );
        }

        return ResponseData.FAILURE(map);
    }

    /**
     * shiro 异常处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler( ShiroException.class )
    public ResponseData shiroErrorHandler(ShiroException ex) {

        ex.printStackTrace();
        if ( ex instanceof UnknownAccountException ){
            return ResponseData.FAILURE(SHIRO_UNKNOWN_ACCOUNT);

        }else if ( ex instanceof LockedAccountException ){
            return ResponseData.FAILURE(SHIRO_LOCKED_ACCOUNT);

        }else if ( ex instanceof UnsupportedTokenException){
            return ResponseData.FAILURE(SHIRO_UNSUPPORTED_TOKEN);

        }else if ( ex instanceof DisabledAccountException){
            return ResponseData.FAILURE(SHIRO_DISABLED_ACCOUNT);

        }else if ( ex instanceof ExcessiveAttemptsException){
            return ResponseData.FAILURE(SHIRO_EXCESSIVE_ATTEMPTS);

        }else if ( ex instanceof ConcurrentAccessException){
            return ResponseData.FAILURE(SHIRO_CONCURRENT_ACCESS);

        }else if ( ex instanceof AccountException ){
            return ResponseData.FAILURE(SHIRO_ACCOUNT);

        }else if ( ex instanceof ExpiredCredentialsException ){
            return ResponseData.FAILURE(SHIRO_EXPIRED_CREDENTIALS);

        }else if ( ex instanceof IncorrectCredentialsException ){
            return ResponseData.FAILURE(SHIRO_INCORRECT_CREDENTIALS);

        }else if ( ex instanceof CredentialsException  ){
            return ResponseData.FAILURE(SHIRO_CREDENTIALS);

        }else if ( ex instanceof AuthorizationException){
            return ResponseData.FAILURE(SHIRO_AUTHORIZATION);

        }else if ( ex instanceof MyExceptionFroAuthentication){
            return ResponseData.FAILURE(new ResponseInterface() {
                @Override
                public Integer getStatusCode() {
                    return ((MyExceptionFroAuthentication) ex).getStatusCode();
                }

                @Override
                public String getName() {
                    return ((MyExceptionFroAuthentication) ex).getName();
                }
            });

        }else {
            return ResponseData.FAILURE(SHIRO_EXCEPTION);
        }

    }

    /**
     * 处理自定义异常
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler( MyException.class )
    public ResponseData handlerMyException (MyException ex) {

        return ResponseData.FAILURE(ex);
    }
}