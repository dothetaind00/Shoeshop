package com.project.repository;


import org.springframework.data.repository.PagingAndSortingRepository;

import com.project.entity.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer>{
	

}
