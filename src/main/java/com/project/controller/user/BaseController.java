package com.project.controller.user;

import com.project.entity.Product;
import com.project.entity.Size;
import com.project.entity.User;
import com.project.service.BannerService;
import com.project.service.ProductService;
import com.project.service.SizeService;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BaseController {

	@Autowired
	private ProductService productService;

	@Autowired
	private BannerService bannerService;


	@GetMapping("/login")
	public String getPageLogin(Model model) {
		return "user/login";
	}

	@GetMapping("/register")
	public String getPageRegister(Model model) {
		model.addAttribute("user", new User());
		return "user/register";
	}

	@GetMapping("/403")
	public String accessDenied() {
		return "403";
	}

	@GetMapping("/")
	public String homePage(Model model, HttpSession session, HttpServletRequest request) {

		Integer amount = (Integer) request.getAttribute("amountIcon");
		System.out.println("" + amount);

		model.addAttribute("listNews", productService.findNewProductByDate());
		model.addAttribute("listNikeShoes", productService.findByBrand(1, 8));
		model.addAttribute("listAdidasShoes", productService.findByBrand(2, 8));

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!auth.getPrincipal().equals("anonymousUser")) {
			session.removeAttribute("listCart");
		}
		model.addAttribute("listBanner",bannerService.findAll());
		return "user/index";
	}

	@GetMapping("/category/{id}")
	public String showProduct(Model model, @PathVariable Integer id) {
		return "user/show-product";
	}

}
