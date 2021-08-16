package com.project.controller.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping(value = "/cart")
public class CartController {

	@Autowired
	ProductService productService;
	
	@Autowired
	CartService cart;
	
	
	@GetMapping(value = "/add")
	public String addCartTets(@RequestParam Integer productId, @RequestParam Integer amount, HttpSession session, Model model) {		
		Product product = productService.findById(productId).orElse(null);	
		@SuppressWarnings("unchecked")
		List<CartDetail> listCart = (List<CartDetail>) session.getAttribute("listCart");
		if(listCart == null) {
			listCart = new ArrayList<CartDetail>();
			CartDetail cartDetail = new CartDetail();
			cartDetail.setPrice(product.getPrice());
			cartDetail.setAmount(amount);
			cartDetail.setProduct(product);
			listCart.add(cartDetail);
		}else {
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
		for (CartDetail cartDetail : listCart) {
			System.out.println(cartDetail.getProduct().getName());
			System.out.println(cartDetail.getPrice());
			System.out.println(cartDetail.getAmount());
			System.out.println(cart.totalAmount(listCart));
		}	
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
		return "user/shop-cart";
	}
	
	@PostMapping(value = "/save")
	public String saveCart(HttpSession session) {
		int totalAmount = (int) session.getAttribute("totalAmount");
		//Cart cart = new Cart(totalAmount, totalCost, user);
		return "shop";
	}
}
