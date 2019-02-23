package com.druidelf.novelmain.common.interceptor.annotationInterceptor.annotationInterceptorBase;



import com.druidelf.novelmain.common.annotation.Interceptors;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"JavaDoc", "Duplicates", "StatementWithEmptyBody"})
@Log4j2
public abstract class GeneralInterceptor extends BaseInterceptor {

    /**
     * 处理并判断注解内容
     * @param request
     * @param response
     * @param annotations
     * @return
     */
    @Override
    public boolean runHandlers(HttpServletRequest request, HttpServletResponse response,Annotation[] annotations) {

        Interceptors interceptorMethod = (Interceptors) annotations[0];
        Interceptors interceptorClass = (Interceptors) annotations[1];

        if(toHander()){//进入注解级别的拦截器
            List<String> names = new ArrayList<>();
            if ( interceptorMethod != null && !interceptorMethod.value().equals("") ) {
                if ( !names.contains( interceptorMethod.value() ) ) names.add( interceptorMethod.value() );

            }else if ( interceptorMethod != null && !interceptorMethod.names()[0].equals("") ){
                for (String s :interceptorMethod.names() ) {
                    if ( !names.contains( s ) ) names.add( s );
                }
            }

            if ( interceptorClass != null && !interceptorClass.value().equals("") ) {
                if ( !names.contains( interceptorClass.value() ) ) names.add( interceptorClass.value() );
            }else if ( interceptorClass != null && !interceptorClass.names()[0].equals("") ){
                for (String s :interceptorClass.names() ) {
                    if ( !names.contains( s ) ) names.add( s );
                }
            }

            return toHanderName( names );
        }

        return true;
    }

    /**
     * 这个方法是用于自定义新的注解的
     * @return
     */
    @Override
    protected Class getAnnotation() {
        // TODO Auto-generated method stub
        return Interceptors.class;
    }

    /**
     * 注解拦截器的拦截内容
     * @return
     */
    private boolean toHander() {
        // System.out.println("进入了注解级别的拦截器");
        return true;
    }

    /**
     * 处理注解拦截器里面的name子拦截器
     * @return
     */
    private boolean toHanderName(List<String> names){
        boolean jduge = false;
        for (String name : names ) {
            String simpleName = getInstance().getClass().getSimpleName();
            simpleName = simpleName.split("\\$\\$")[0];
            if(name.equals(simpleName)) {//这个判断是为了让注解的name和该类的名称是否一致
                jduge = true;
                break;
            }
        }
        if (jduge){ // 判断是否有注解，然后执行下一步操作
            postHandleByAnnotation();
            afterCompletionByAnnotation();
            afterConcurrentHandlingStartedByAnnotation();
            return interceptorToDo();
        }
        return true;
    }

    /**
     * 注解拦截器注册
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("注册拦截器:"+getInstance ().getClass().getSimpleName().split("\\$\\$")[0]);
        registry.addInterceptor(getInstance ());
    }

    /**
     * 得到子拦截器实例
     * @return
     */
    protected abstract HandlerInterceptor getInstance ();

    /**
     * 子拦截器实现
     * @return
     */
    public abstract boolean interceptorToDo();


    @Override
    protected void postHandleByAnnotation() {

    }

    @Override
    protected void afterCompletionByAnnotation() {

    }

    @Override
    protected void afterConcurrentHandlingStartedByAnnotation() {

    }
}