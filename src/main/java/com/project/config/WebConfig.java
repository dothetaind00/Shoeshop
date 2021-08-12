package com.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.project.utils.MenuHandleInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
//	@Bean
//	public MenuHandleInterceptor menuHandleInterceptor() {
//		return new MenuHandleInterceptor();
//	}
	@Autowired
	private MenuHandleInterceptor menuHandleInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(menuHandleInterceptor).addPathPatterns("/**");
	}
	
	
	
	

}
