package com.project.controller.user;

import com.project.entity.Product;
import com.project.entity.Role;
import com.project.entity.Size;
import com.project.entity.User;
import com.project.service.CategoryService;
import com.project.service.ProductService;
import com.project.service.RoleService;
import com.project.service.SizeService;
import com.project.service.UserService;
import com.project.service.sendmail.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@Controller
public class BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private SendMail sendMail;

	@Autowired
	private ProductService productService;

	@Autowired
	private SizeService sizeService;

	@GetMapping("/login")
	public String getPageLogin(Model model) {
		return "user/login";
	}

	@GetMapping("/register")
	public String getPageRegister(Model model) {
		model.addAttribute("user", new User());
		return "user/register";
	}

	@PostMapping("/create-user")
	public String doRegister(@ModelAttribute @Valid User user, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/register?invalid";
		}

		User u = userService.save(user);
		if (u == null)
			return "redirect:/register?existed";

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String url = "<a href=\"http://localhost:8080/actived/" + u.getToken() + "\">Actice account !</a>";
					sendMail.confirmMail(u.getEmail(), url);
					Thread.sleep(10 * 60 * 1000);
					if (userService.existUserByTokenAndIsEnable(u.getToken(), false)) {
						userService.delete(u.getId());
					}
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		});
		thread.start();

		return "redirect:/login";
	}

	@GetMapping("/actived/{token}")
	public String activeUser(@PathVariable(value = "token") String token) {
		User user = userService.findUserByTokenAndIsEnable(token, false).orElse(null);
		if (user != null) {
			// add role for user
			Role role = roleService.findRoleByRole("USER");
			user.addRole(role);
			user.setIsEnable(true);
			userService.save(user);
			return "redirect:/login?actived";
		}
		return "redirect:/login?expiredToken";
	}

	@GetMapping("/contact")
	public String getContact() {
		return "redirect:/user/contact";
	}

	@GetMapping("/403")
	public String accessDenied() {
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
		int pageSize = 8;

		Page<Product> page;	
		
		if (id == null || id == 0) {
			page = productService.findPaginated(pageNo, pageSize);
			model.addAttribute("listShoes", productService.findAll());
		} else {
			page = productService.findByCategory(id, pageNo, pageSize);
		}
		List<Product> listProducts = page.getContent();
		model.addAttribute("category_id", id);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("list", listProducts);
		return "user/show-product";
	}

}
