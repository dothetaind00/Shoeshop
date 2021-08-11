package com.project.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.project.entity.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer>{
	@Query(value = "Select * from product where name Like %?1% ", nativeQuery = true)
	public List<Product> findAllProductByName(String name);
	
	@Query(value = "Select * from product where name Like %?1% ", nativeQuery = true)
	Page<Product> findAllByName(String name, Pageable pageable);
		
}
