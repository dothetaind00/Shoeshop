package com.project.service;

import java.util.List;
import java.util.Optional;
import com.project.entity.Brand;


public interface BrandService {

	Brand getById(Integer id);

	void deleteAll();

	void delete(Brand entity);

	void deleteById(Integer id);

	long count();

	boolean existsById(Integer id);

	Optional<Brand> findById(Integer id);

	List<Brand> findAll();

	Brand save(Brand entity);

	Optional<Brand> findByName(String name);

	
}
