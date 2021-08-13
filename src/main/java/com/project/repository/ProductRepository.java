package com.project.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.project.entity.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer>{

	
	@Query(value = "Select * from product where name Like %?1% ", nativeQuery = true)
	Page<Product> findAllByName(String name, Pageable pageable);
	
	@Query(value = "select * from shoedatabase.product where datediff(curdate(), oncreate) <= 7 and is_enable = true LIMIT 8", nativeQuery = true)
	List<Product> findNewProductByDate();
	
	
	@Query(value = "select * from shoedatabase.product where datediff(curdate(), oncreate) <= 7 and brand_id = ?1 and is_enable = true  LIMIT ?2", nativeQuery = true)
	List<Product> findByBrand(Integer id, int number);
	
	@Query(value = "select * from shoedatabase.product where category_id = ?1 and is_enable = true", nativeQuery = true)
	Page<Product> findByCategory(Integer id, Pageable pageable);
	
		
}
