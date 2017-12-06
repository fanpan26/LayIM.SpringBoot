package com.fyp.layim.web.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class LayimApiConfig extends WebMvcConfigurerAdapter{

    @Bean
    public TokenVerifyInterceptor tokenVerifyInterceptor(){
        return  new TokenVerifyInterceptor();
    }

    /**
     * 拦截API的请求
     * */
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(tokenVerifyInterceptor()).addPathPatterns("/api/layim/**");
        super.addInterceptors(registry);
    }
}
