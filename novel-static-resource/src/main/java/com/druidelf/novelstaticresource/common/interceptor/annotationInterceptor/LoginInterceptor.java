package com.druidelf.novelstaticresource.common.interceptor.annotationInterceptor;

import com.druidelf.novelstaticresource.common.interceptor.annotationInterceptor.annotationInterceptorBase.GeneralInterceptor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

@Log4j2
@Configuration
public class LoginInterceptor extends GeneralInterceptor {

    @Override
    protected HandlerInterceptor getInstance() {
        return this;
    }

    @Override
    public boolean interceptorToDo() {
        return true;
    }


}
