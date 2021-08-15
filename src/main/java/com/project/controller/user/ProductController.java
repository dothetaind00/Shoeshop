package com.project.controller.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.entity.Product;
import com.project.entity.Size;
import com.project.service.ProductService;
import com.project.service.SizeService;

@Controller(value = "productOfUser")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private SizeService sizeService;

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

	@GetMapping("/cart/{id}")
	public String addCart(Model model, @PathVariable Integer id, @RequestParam(name = "size", required = false) Integer size_id,
			@RequestParam(name = "amount", required = false) Integer amount) {
		System.out.println("product " + id);
		System.out.println("Amount" + amount);
		if(StringUtils.hasText(size_id + "")) {
			System.out.println("Size "+ size_id);
		}

		return "user/shop-cart";
	}

}
