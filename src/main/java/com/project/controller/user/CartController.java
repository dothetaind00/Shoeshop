package com.project.controller.user;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.entity.Cart;
import com.project.entity.CartDetail;
import com.project.entity.Product;
import com.project.service.CartService;
import com.project.service.ProductService;
import com.project.entity.User;
import com.project.repository.CartDetailRepository;
import com.project.repository.CartRepository;
import com.project.service.CartService;
import com.project.service.ProductService;
import com.project.service.UserService;


@Controller
@RequestMapping(value = "/cart")
public class CartController {

	@Autowired
	ProductService productService;

	@Autowired
	CartService cart;

	@Autowired
	UserService userService;

	@Autowired
	CartRepository cartRepository;

	@Autowired
	CartDetailRepository cartDetailRepository;

	@GetMapping(value = "/add")
	public String addCartTets(@RequestParam Integer productId, @RequestParam Integer amount, HttpSession session,
			Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Optional<User> user = userService.findUserByUserName(auth.getName());
		Product product = productService.findById(productId).orElse(null);
		@SuppressWarnings("unchecked")
		List<CartDetail> listCart = (List<CartDetail>) session.getAttribute("listCart");
		if (listCart == null) {
			listCart = new ArrayList<CartDetail>();
			CartDetail cartDetail = new CartDetail();
			cartDetail.setPrice(product.getPrice());
			cartDetail.setAmount(amount);
			cartDetail.setProduct(product);
			listCart.add(cartDetail);
		} else {
			boolean existItem = false;
			for (int i = 0; i < listCart.size(); i++) {
				if (listCart.get(i).getProduct().getId() == productId) {
					listCart.get(i).setAmount(listCart.get(i).getAmount() + amount);
					existItem = true;
					break;
				}
			}
			if (!existItem) {
				CartDetail cartDetail = new CartDetail();
				cartDetail.setPrice(product.getPrice());
				cartDetail.setAmount(amount);
				cartDetail.setProduct(product);
				listCart.add(cartDetail);
			}
		}
		session.setAttribute("listCart", listCart);
		session.setAttribute("totalAmount", cart.totalAmount(listCart));
		double totalAmount = (double) session.getAttribute("totalAmount");
		session.setAttribute("cart", new Cart(1, totalAmount, user.get().getId()));
		saveCart(session);
		return "user/shop-cart";
	}

	@GetMapping(value = "/update")
	public String updateCart(HttpSession session, HttpServletRequest request) {
		String[] itemUpdate = request.getParameterValues("amount");
		List<CartDetail> listCart = (List<CartDetail>) session.getAttribute("listCart");
		for (int i = 0; i < listCart.size(); i++) {
			listCart.get(i).setAmount(Integer.parseInt(itemUpdate[i]));
		}
		session.setAttribute("listCart", listCart);
		session.setAttribute("totalAmount", cart.totalAmount(listCart));
		saveCart(session);
		return "user/shop-cart";
	}

	@RequestMapping(value = "/remove/{id}")
	public String deleteItems(@PathVariable(value = "id") int productID, HttpSession session) {
		List<CartDetail> listCart = (List<CartDetail>) session.getAttribute("listCart");
		for (int i = 0; i < listCart.size(); i++) {
			if (listCart.get(i).getProduct().getId() == productID) {
				listCart.remove(i);
				break;
			}
		}
		session.setAttribute("listCart", listCart);
		session.setAttribute("totalAmount", cart.totalAmount(listCart));
		saveCart(session);
		System.out.println(listCart.isEmpty());
		return "user/shop-cart";
	}

	// save cart vao db
	public String saveCart(HttpSession session) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		double totalAmount = (double) session.getAttribute("totalAmount");
		if (auth.getPrincipal().equals("anonymousUser")) {
			System.out.println("test");
		} else {
			Optional<User> user = userService.findUserByUserName(auth.getName());
			if (user.isPresent()) {
				Cart cart = (Cart) session.getAttribute("cart");
				cart.setTotalCost(totalAmount);;
				cartRepository.save(cart);
				List<CartDetail> listCart = (List<CartDetail>) session.getAttribute("listCart");
				for (CartDetail cartDetail : listCart) {
					cartDetail.setCart(cart);
					System.out.println(cartDetail.getPrice());
					System.out.println(cartDetail.getAmount());
					cartDetailRepository.save(cartDetail);
				}
			}
		}
		return "success";
	}
}
