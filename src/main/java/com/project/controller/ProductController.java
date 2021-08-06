package com.project.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ProductController {
	
	@GetMapping("/product")
	public String productHome(ModelMap model) {
//		model.addAttribute("list", categoryService.findAll());
		return "product";
	}
	

}
