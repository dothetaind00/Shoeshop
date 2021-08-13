package com.project.controller.user;

import com.project.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {

    @GetMapping("/login")
    public String getPageLogin(Model model){

//    	model.addAttribute("menu",categoryService.findByIsDisplay(true));
        return "user/login";
    }

<<<<<<< HEAD
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
=======
    @GetMapping("/register")
    public String getPageRegister(Model model){
        model.addAttribute("user", new User());
        return "user/register";
    }

    @GetMapping("/contact")
    public String getContact(){
        return "redirect:/user/contact";
    }
>>>>>>> 3e5bc186989d54c6bae9a2433430a63135fc9f5d

    @GetMapping("/403")
    public String accessDenied(){
        return "403";
    }  
}
