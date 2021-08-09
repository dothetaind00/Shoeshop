package com.project.controller.admin;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.entity.Category;
import com.project.service.CategoryService;

@Controller
@RequestMapping("/admin")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	
	
	@GetMapping("/category")
	public String categoryHome(ModelMap model) {
		model.addAttribute("list", categoryService.findAll());	
		return "admin/category";
	}
	
	@GetMapping("/category/addcategory")
	public String addOrEdit(ModelMap model) {
		Category category = new Category();
		model.addAttribute("category", category);
		return "admin/addcategory";
	}
	// Save or Update Category
	@PostMapping("/category/savecategory")
	public String addOrUpdate(ModelMap model, @ModelAttribute("category") Category category) {	

		if(category.getId() == null) {
			
			if(categoryService.findByName(category.getName()).isPresent()) {
				// in ra thong bao
				// Trang bao loi
			}else {
				categoryService.save(category);
			}
		}else {
			categoryService.save(category);
		}
		return "redirect:/admin/category";
	}
	
	// Delete Category
	@RequestMapping("/deletecategory/{id}")
	public String delete(ModelMap model, @PathVariable Integer id) {
		categoryService.deleteById(id);
		return "redirect:/admin/category";
	}
	
	@RequestMapping("/category/edit/{id}")
	public String edit(ModelMap model, @PathVariable Integer id) {
		Optional<Category> cate = categoryService.findById(id);
		// Check Category Exit or not
		if(cate.isPresent()) {
			// Exist
			model.addAttribute("category", cate.get());
			return "admin/addcategory";
		}else {
			// Not Exist
			// Error Page
			return "redirect:/admin/category";
		}
		
		
	}
			

}
