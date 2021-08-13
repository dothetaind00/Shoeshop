package com.project.controller.user;

import com.project.entity.Product;
import com.project.entity.Size;
import com.project.entity.User;
import com.project.service.ProductService;
import com.project.service.SizeService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BaseController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private SizeService sizeService;

    @GetMapping("/login")
    public String getPageLogin(Model model){

//    	model.addAttribute("menu",categoryService.findByIsDisplay(true));
        return "user/login";
    }

    @GetMapping("/register")
    public String getPageRegister(Model model){
        model.addAttribute("user", new User());
        return "user/register";
    }

    @GetMapping("/contact")
    public String getContact(){
        return "redirect:/user/contact";
    }


    @GetMapping("/403")
    public String accessDenied(){
        return "403";
    }  
    
	@GetMapping("/")
	public String homePage(Model model) {
		model.addAttribute("listNews", productService.findNewProductByDate());
		model.addAttribute("listNikeShoes", productService.findByBrand(2, 8));
		return "user/index";
	}

	@GetMapping("/product/{id}")
	public String productDetail(Model model, @PathVariable Integer id) {
		Optional<Product> product = productService.findById(id);

		if (product.isPresent()) {

			// get list size of shoes
			List<Size> listSize = sizeService.findByProductAvailable(id);
			if (listSize.isEmpty()) {

				model.addAttribute("status", "Hết hàng");

			} else {

				model.addAttribute("status", "Còn hàng");
				model.addAttribute("listSize", listSize);

			}

			model.addAttribute("listShoes", productService.findByBrand(product.get().getBrand().getId(), 4));
			model.addAttribute("product", product.get());
			return "user/product-details";
		} else {
			return "403";
		}
	}

	@GetMapping("/category/{id}")
	public String showProduct(Model model, @PathVariable Integer id) {
//		int pageSize = 8;
//
//		Page<Product> page;	
//		
//		if (id == null || id == 0) {
//			page = productService.findPaginated(pageNo, pageSize);
//			model.addAttribute("listShoes", productService.findAll());
//		} else {
//			page = productService.findByCategory(id, pageNo, pageSize);
//		}
//		List<Product> listProducts = page.getContent();
//		model.addAttribute("category_id", id);
//		model.addAttribute("currentPage", pageNo);
//		model.addAttribute("totalPages", page.getTotalPages());
//		model.addAttribute("totalItems", page.getTotalElements());
//		model.addAttribute("list", listProducts);
		return "user/show-product";
	}
    
}
