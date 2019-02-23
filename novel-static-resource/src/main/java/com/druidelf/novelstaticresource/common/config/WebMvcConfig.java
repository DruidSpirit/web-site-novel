package com.druidelf.novelstaticresource.common.config;


import com.druidelf.novelstaticresource.common.interceptor.annotationInterceptor.ResourceInterceptor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Data
@Log4j2
@Configuration
@ConfigurationProperties("web-mvc-config")
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 跨域设置
     */
    private String[] origins;
    /**
     * 跨域设置
     * @param registry
     */
    private String localFileServerDir;
    private String localFileServerPath[];
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        boolean allowCredentials = true;
        if ( origins == null || origins.length == 0) {
            allowCredentials = false;
            origins = new String[]{"*"};
        }
        registry.addMapping("/**")
                .allowedOrigins(origins)
                .allowedMethods("PUT", "POST", "GET", "OPTIONS", "DELETE")
                .allowedHeaders("Content-Type", "Access-Token")
                .exposedHeaders("**")
                .allowCredentials(allowCredentials).maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ResourceInterceptor());
       // registry.addInterceptor(new CrossDomainInterceptor());
    }

    //访问图片方法
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(this.getLocalFileServerPath())
                .addResourceLocations("file:" + this.toServerPath(this.getLocalFileServerDir()) + "/");

//         本地文件夹要以"flie:" 开头，文件夹要以"/" 结束，example：
//        registry.addResourceHandler("/abc/**").addResourceLocations("file:D:/pdf/");
    }
    /**
     * 文件实际路径转为服务器url路径
     * @param absolutePath
     * @return
     */
    public String toServerPath(String absolutePath) {
        absolutePath = absolutePath.replaceAll("\\\\", "/");
        return "/" + absolutePath;
    }
}
