package com.project.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.entity.Product;
import com.project.firebase.StorageStrategy;
import com.project.repository.ProductRepository;
import com.project.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	StorageStrategy storageStrategy;
	
	@Override
	public Product save(Product entity) {
		return productRepository.save(entity);
	}

	@Override
	public List<Product> findAll() {
		return (List<Product>) productRepository.findAll();
	}

	@Override
	public Optional<Product> findById(Integer id) {
		return productRepository.findById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return productRepository.existsById(id);
	}

	@Override
	public long count() {
		return productRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		productRepository.deleteById(id);
	}

	@Override
	public void delete(Product entity) {
		productRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		productRepository.deleteAll();
	}

	@Override
	public String saveImageUrl(MultipartFile file) {			
		StringBuilder imageUrl = new StringBuilder();
		 try {
	            String fileName = storageStrategy.saveImage(file, "product");
	                      
	            if (fileName != null || fileName.trim().length() != 0) {
	                String tokens = StringUtils.substringBeforeLast(fileName, ".");
	                imageUrl.append("https://firebasestorage.googleapis.com/v0/b/shoe-mock-project.appspot.com/o/");
	                imageUrl.append("product%2F");
	                imageUrl.append(fileName);
	                imageUrl.append("?alt=media&token=");
	                imageUrl.append(tokens);
	            }

	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
		return imageUrl.toString();
	}

	@Override
	public List<Product> findAllProductByName(String name) {
		return productRepository.findAllProductByName(name);
	}


	@Override
	public Page<Product> findPaginated(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		return this.productRepository.findAll(pageable);
	}

	@Override
	public Page<Product> findAllByName(String name, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		return this.productRepository.findAllByName(name, pageable);
	}


	
	
}
