package com.druidelf.novelbackstagemanagement.common.interceptor.annotationInterceptor.annotationInterceptorBase;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;


@SuppressWarnings({"JavaDoc", "UnusedAssignment", "RedundantThrows"})
public abstract class BaseInterceptor extends HandlerInterceptorAdapter implements WebMvcConfigurer {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return dealWithAnnotations( request, response, handler );
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        postHandleByAnnotation ();
        super.postHandle(request, response, handler, modelAndView);
    }

    protected abstract void postHandleByAnnotation ();

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        afterCompletionByAnnotation ();
        super.afterCompletion(request, response, handler, ex);
    }

    protected abstract void afterCompletionByAnnotation ();

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        afterConcurrentHandlingStartedByAnnotation ();
        super.afterConcurrentHandlingStarted(request, response, handler);
    }

    protected abstract void afterConcurrentHandlingStartedByAnnotation ();

    /**
     * 处理注解拦截器的处理形式
     * @return
     */
    private boolean dealWithAnnotations ( HttpServletRequest request, HttpServletResponse response, Object handler ) {
        Annotation[] interceptors = isMyHandler(handler);
        if ( interceptors == null ) return true;

        if ( interceptors[0] !=null || interceptors[1] != null ) {
            return  runHandlers(request, response,interceptors);
        }
        return true;
    }

    /**
     * 得到控制层注解
     * @param handler
     * @return
     */
    private Annotation[] isMyHandler(Object handler) {
        Annotation interceptorMethod = null;
        Annotation interceptorClass = null;

        if (!(handler instanceof HandlerMethod)){
            return null;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        interceptorMethod = handlerMethod.getMethodAnnotation(getAnnotation());

        interceptorClass = handlerMethod.getMethod().getDeclaringClass().getAnnotation(getAnnotation());

        if (interceptorMethod != null && !interceptorMethod.annotationType().isAnnotation()){
            interceptorMethod = null;
        }
        if (interceptorClass != null && !interceptorClass.annotationType().isAnnotation()){
            interceptorClass = null;
        }

        return new Annotation[]{interceptorMethod,interceptorClass };
    }

    /**
     * 这个抽象方法是为了方便子类重写
     * @param request
     * @param response
     * @param annotations
     * @return
     */
    protected abstract boolean runHandlers(HttpServletRequest request, HttpServletResponse response,Annotation[] annotations);

    /**
     * 得到自定义注解
     * @return
     */
    protected abstract Class getAnnotation();

}

