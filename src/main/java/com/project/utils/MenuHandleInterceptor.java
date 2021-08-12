package com.project.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.project.service.CategoryService;

@Component
public class MenuHandleInterceptor implements HandlerInterceptor {
	
	@Autowired
	CategoryService categoryService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {	
		request.setAttribute("menu", categoryService.findByIsDisplay(true));		
		return true;
	}
}
