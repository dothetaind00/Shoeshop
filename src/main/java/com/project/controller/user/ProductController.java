package com.project.controller.user;

import java.util.List;
import java.util.Optional;

import com.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.entity.CartDetail;
import com.project.entity.Product;
import com.project.entity.Size;
import com.project.service.BrandService;
import com.project.service.CategoryService;
import com.project.service.ProductService;
import com.project.service.SizeService;

@Controller(value = "productOfUser")
public class ProductController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private BrandService brandService;

	@Autowired
	private ProductService productService;

	@Autowired
	private SizeService sizeService;

	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/api/product")
	public ResponseEntity<List<Product>> getProduct(){
		List<Product> products = productRepository.show();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping("/product/{id}")
	public String productDetail(Model model, @PathVariable Integer id) {
		Optional<Product> product = productService.findById(id);
		
		// check product is exist or not
		if (product.isPresent()) {
			CartDetail cartDetail = new CartDetail();
			// get list size of shoes
			List<Size> listSize = sizeService.findByProductAvailable(id);
			if (listSize.isEmpty()) {

				model.addAttribute("status", "Hết hàng");

			} else {

				model.addAttribute("status", "Còn hàng");
				model.addAttribute("listSize", listSize);

			}
			
			//update view product
			product.get().setView(product.get().getView()+1);		
			productService.save(product.get());

			model.addAttribute("listShoes", productService.findByBrand(product.get().getBrand().getId(), 4));
			model.addAttribute("product", product.get());
			model.addAttribute("cartDetail", cartDetail);

			return "user/product-details";
		} else {
			model.addAttribute("mess", "Không tồn tại sản phẩm này này");
			return "error";
		}
	}
	
	@GetMapping("/product/page/{pageNo}")
	public String showProduct(Model model,
			@PathVariable Integer pageNo, 
			@RequestParam(name = "category", required = false) String category_id,
			@RequestParam(name = "brand", required = false) String brand_id,
			@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "minAmount", required = false) String minAmount,
			@RequestParam(name = "maxAmount", required = false) String maxAmount) {
		
		int pageSize = 9;

		Page<Product> page;
				
		page = productService.filterProduct(category_id, brand_id, keyword, minAmount, maxAmount, pageNo, pageSize);
		
		model.addAttribute("minAmount", minAmount);
		model.addAttribute("maxAmount", maxAmount);
		model.addAttribute("keyword", keyword);
		model.addAttribute("category", category_id);
		model.addAttribute("brand", brand_id);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listProduct", page.getContent());
		
		
		model.addAttribute("listCategory", categoryService.findAll());
		model.addAttribute("listBrand", brandService.findAll());
		return "user/shop";
	}

	
	

}
