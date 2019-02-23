package com.druidelf.novelstaticresource.common.config;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * 声明式事物配置
 */
@Aspect
@Log4j2
@Configuration
@Data
@ConfigurationProperties("transaction-advice-config")
public class TransactionAdviceConfig {

    @Autowired
    private PlatformTransactionManager transactionManager;
    /**
     * 切入点表达式
     */
    private String aopPointcutExpression = "execution (* com.druidelf.novelstaticresource.service.*.*(..))";
    /**
     * 查询事物前缀
     */
    private String [] selectPrefix = {"get"};
    /**
     * 数据变动前缀
     */
    private String [] changeDataPrefix = {"save"};

    @Bean
    public TransactionInterceptor txAdvice() {

        DefaultTransactionAttribute txAttr_REQUIRED = new DefaultTransactionAttribute();
        txAttr_REQUIRED.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        DefaultTransactionAttribute txAttr_REQUIRED_READONLY = new DefaultTransactionAttribute();
        txAttr_REQUIRED_READONLY.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        txAttr_REQUIRED_READONLY.setReadOnly(true);

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        // 打印日志提示
        String selectPrefixs = "";
        String changeDataPrefixs = "";

        for (String s : changeDataPrefix ) {
            source.addTransactionalMethod(s, txAttr_REQUIRED);
            changeDataPrefixs += s + ",";
        }
        for (String s : selectPrefix ) {
            source.addTransactionalMethod(s, txAttr_REQUIRED_READONLY);
            selectPrefixs += s + ",";
        }

        selectPrefixs.substring(0,selectPrefixs.length()-1);
        changeDataPrefixs.substring(0,changeDataPrefixs.length()-1);
//        System.out.println("\033[30;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[31;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[32;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[33;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[34;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[35;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[36;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[37;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[40;31;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[41;32;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[42;33;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[43;34;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[44;35;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[45;36;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[46;37;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[47;4m" + "我滴个颜什" + "\033[0m");

        System.out.println("\033[45;36;4m" + "事物说明:" + "\033[0m");
        System.out.println("\033[43;34;4m" + "\t\t对方法名称的前缀为"+changeDataPrefixs+"进行了数据变动事物声明" + "\033[0m");
        System.out.println("\033[43;34;4m" + "\t\t对方法名称的前缀为"+selectPrefixs+"进行了数据查看事物声明" + "\033[0m");
        log.trace("事物说明:\n\t\t\t\t\t\t\\对方法名称的前缀为"+changeDataPrefixs+"进行了数据变动事物声明\n" +
                "\t\t\t\t\t\t对方法名称的前缀为"+selectPrefixs+"进行了数据查看事物声明\\");
        return new TransactionInterceptor(transactionManager, source);
    }

    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(aopPointcutExpression);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }
}
