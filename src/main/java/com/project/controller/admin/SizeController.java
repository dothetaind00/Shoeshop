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

import com.project.entity.Product;
import com.project.entity.Size;
import com.project.service.ProductService;
import com.project.service.SizeService;

@Controller
@RequestMapping("/admin/product")
public class SizeController {

	@Autowired
	SizeService sizeService;

	@Autowired
	ProductService productService;

	@GetMapping("/size/{id}")
	public String getAllSize(Model model, @PathVariable Integer id) {

		Optional<Product> product = productService.findById(id);

		if (product.isPresent()) {
			model.addAttribute("product", product.get());
			model.addAttribute("list", sizeService.findByProductId(id));
		} else {
			// khong co product
			// sang trang loi
		}

		return "admin/size";
	}

	@GetMapping("/addsize/{id}")
	public String addSize(Model model, @PathVariable Integer id) {

		Optional<Product> product = productService.findById(id);

		// check product exist
		if (product.isPresent()) {

			Size size = new Size();
			size.setProduct(product.get());
			size.setAmount(0);

			model.addAttribute("product", product.get());
			model.addAttribute("size", size);
			return "admin/addsize";
		} else {
			// khong co product
			// sang trang loi
			return null;
		}

	}

	@PostMapping("/savesize")
	public String saveSize(Model model, @ModelAttribute(name = "size") Size size) {

		// check add or update
		if (size.getId() == null) {

			Optional<Size> s = sizeService.findBySizeAndProductId(size.getSize(), size.getProduct().getId());

			if (s.isPresent()) {
				// already have this size so dont add show message error
				// or add more amount for that size
			} else {
				// if dont have so we add new size
				sizeService.save(size);

			}

		} else {
			// if id != null => admin want update
			sizeService.save(size);
		}

		return "redirect:/admin/product/size/" + size.getProduct().getId();
	}

	// edit size
	@GetMapping("/editsize/{id}")
	public String editSize(Model model, @PathVariable Integer id) {
		Optional<Size> size = sizeService.findById(id);
		if(size.isPresent()) {
			
			model.addAttribute("size",size.get());
			return "admin/editsize";
			
		}else {
			// dont have this size
			// error page
			model.addAttribute("mess", "Không tồn tại size này");
			return "error";
		}
		
	}

	// delete size
	@GetMapping("/deletesize/{id}")
	public String deleteSize(Model model, @PathVariable Integer id) {
		Optional<Size> size = sizeService.findById(id);
		if(size.isPresent()) {
			sizeService.deleteById(id);
			return "redirect:/admin/product/size/" + size.get().getProduct().getId();
		}else {
			// don't have this size
			// error
			model.addAttribute("mess", "Không tồn tại size này");
			return "error";
		}
		
	}

}
