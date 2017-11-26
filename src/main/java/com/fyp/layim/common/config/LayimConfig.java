package com.fyp.layim.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author fyp
 * @crate 2017/11/1 20:28
 * @project SpringBootLayIM
 * @EnableWebMvc 这里不要加这个注解，否则static 和一些默认的也会失效
 */
//@Configuration
//public class LayimConfig extends WebMvcConfigurerAdapter {
//
//    /**
//     * 根目录指向index.html
//     * */
////    @Override
////    public void addViewControllers(ViewControllerRegistry registry) {
////        registry.addRedirectViewController("/","/layim/index.html");
////    }
//
//    /**
//     * 自己定义的静态资源映射到layim文件夹
//     * */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/layim/**").addResourceLocations("classpath:/layim/");
//        super.addResourceHandlers(registry);
//    }
//}
