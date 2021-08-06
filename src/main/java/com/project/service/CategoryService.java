package com.project.service;

import java.util.List;
import java.util.Optional;

import com.project.entity.Category;


public interface CategoryService {

	Category getById(Integer id);

	void deleteAll();

	void delete(Category entity);

	void deleteById(Integer id);

	long count();

	boolean existsById(Integer id);

	List<Category> saveAll(List<Category> entities);

	Optional<Category> findById(Integer id);

	List<Category> findAllById(List<Integer> ids);

	List<Category> findAll();

	Category save(Category entity);
	
	Optional<Category> findByName(String name);

	
	

}
