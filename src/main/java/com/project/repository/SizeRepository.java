package com.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.entity.Size;

public interface SizeRepository extends JpaRepository<Size, Integer>{
	@Query(value = "Select * from size where size = ?1 and product_id = ?2", nativeQuery = true)
	Optional<Size> findBySizeAndProductId(String size,Integer id);
	
	@Query(value = "Select * from size where product_id = ?1", nativeQuery = true)
	List<Size> findByProduct(Integer id);
	
	
	@Query(value = "Select * from size where product_id = ?1 and amount > 0", nativeQuery = true)
	List<Size> findByProductAvailable(Integer id);
	
	
	
	
	
}
