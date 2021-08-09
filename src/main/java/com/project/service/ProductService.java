package com.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.entity.Product;
import com.project.repository.ProductRepository;

public interface ProductService {



	void deleteAll();

	void delete(Product entity);

	void deleteById(Integer id);

	long count();

	boolean existsById(Integer id);

	Optional<Product> findById(Integer id);

	List<Product> findAll();

	Product save(Product entity);



}
