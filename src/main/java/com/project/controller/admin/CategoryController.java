package com.project.controller.admin;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.entity.Category;
import com.project.service.CategoryService;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	
	
	@GetMapping("")
	public String categoryHome(Model model) {
		model.addAttribute("list", categoryService.findAll());	
		return "admin/category";
	}
	
	@GetMapping("/addcategory")
	public String addOrEdit(Model model) {
		Category category = new Category();
		category.setIsDisplay(true);
		model.addAttribute("category", category);
		return "admin/addcategory";
	}
	// Save or Update Category
	@PostMapping("/savecategory")
	public String addOrUpdate(Model model, @ModelAttribute("category") Category category) {	

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
	@GetMapping("/delete/{id}")
	public String delete(Model model, @PathVariable Integer id) {
		categoryService.deleteById(id);
		return "redirect:/admin/category";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable Integer id) {
		Optional<Category> cate = categoryService.findById(id);
		// Check Category Exit or not
		if(cate.isPresent()) {
			// Exist
			model.addAttribute("category", cate.get());
			return "admin/editcategory";
		}else {
			// Not Exist
			// Error Page
			model.addAttribute("mess", "Không tồn tại thương hiệu này");
			return "error";
		}		
	}
	
	
			

}
