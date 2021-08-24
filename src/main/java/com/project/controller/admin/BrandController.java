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
import com.project.entity.Brand;
import com.project.service.BrandService;


@Controller
@RequestMapping("/admin/brand")
public class BrandController {
	@Autowired
	BrandService brandService;
	
	
	@GetMapping("")
	public String brandHome(ModelMap model) {
		model.addAttribute("list", brandService.findAll());
		return "admin/brand";
	}
	
	@GetMapping("/add")
	public String addOrEdit(ModelMap model) {
		Brand brand = new Brand();
		brand.setIsDisplay(true);
		model.addAttribute("brand", brand);
		return "admin/addbrand";
	}
	// Save or Update Brand
	@PostMapping("/savebrand")
	public String addOrUpdate(ModelMap model, @ModelAttribute("brand") Brand brand) {
		
		if(brand.getId() == null) {
			
			if(brandService.findByName(brand.getName()).isPresent()) {
				// in ra thong bao
				// Trang bao loi
				
			}else {

				brandService.save(brand);
			}
		}else {		
			brandService.save(brand);
		}
		return "redirect:/admin/brand";
	}
	// Delete brand
	@GetMapping("/delete/{id}")
	public String delete(ModelMap model, @PathVariable Integer id) {
		//		brandService.deleteById(id);

		// we not delete we just disable it.
		Optional<Brand> brand = brandService.findById(id);
		// Check brand Exit or not
		if(brand.isPresent()) {
			// Exist
			brand.get().setIsDisplay(false);
			brandService.save(brand.get());
			return "redirect:/admin/brand";
		}else {
			// Not Exist
			// Error Page
			model.addAttribute("mess", "Không tồn tại thương hiệu này");
			return "error";
		}
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(ModelMap model, @PathVariable Integer id) {
		Optional<Brand> brand = brandService.findById(id);
		// Check brand Exit or not
		if(brand.isPresent()) {
			// Exist
			model.addAttribute("brand", brand.get());
			return "admin/editbrand";
		}else {
			// Not Exist
			// Error Page
			model.addAttribute("mess", "Không tồn tại thương hiệu này");
			return "error";
		}
		
		
	}
}
