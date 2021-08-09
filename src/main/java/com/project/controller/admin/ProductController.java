package com.project.controller.admin;


import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.entity.Category;
import com.project.entity.Product;
import com.project.service.BrandService;
import com.project.service.CategoryService;
import com.project.service.ProductService;

@Controller
@RequestMapping("/admin")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	BrandService brandService;
	
	
	@GetMapping("/product")
	public String productHome(ModelMap model) {
		model.addAttribute("list", productService.findAll());
		return "admin/product";
	}
	
	@GetMapping("/product/addproduct")
	public String addOrEdit(Model model) {
		Product product = new Product();
		// set enable
		product.setIsEnable(1);
		
		// set time stamp
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		product.setOnCreate(timestamp);
		product.setOnUpdate(timestamp);
	
		
		model.addAttribute("product", product);
		return "admin/editproduct";
	}
	
	
	

}
