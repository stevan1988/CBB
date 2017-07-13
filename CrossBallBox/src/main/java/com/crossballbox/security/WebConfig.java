package com.crossballbox.security;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.crossballbox.util.NotificationInterceptor;

public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
	}
	
	//interceptor for admin controller
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(new NotificationInterceptor()).addPathPatterns("/admin");
    }

}
