package com.lfb.interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author lfb
 * @data 2022/8/19 16:35
 */
@Configuration
@Deprecated
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*过滤路径*/
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/admin/**")  //过滤路径
                .excludePathPatterns("/admin")    //非过滤路径
                .excludePathPatterns("/admin/login");
    }
}
