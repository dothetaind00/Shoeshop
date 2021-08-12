package com.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	// Query tim kieu theo ten
	@Query(value = "select * from category where name = ?1", nativeQuery = true)
		public Optional<Category> findByName(String name);
	
	List<Category> findByIsDisplay(Boolean isDisplay);
}
