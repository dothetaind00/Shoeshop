package com.project.service.impl;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entity.Brand;
import com.project.repository.BrandRepository;
import com.project.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService {
	
	@Autowired
	BrandRepository brandRepository;
	
	@Override
	public Optional<Brand> findByName(String name) {
		return brandRepository.findByName(name);
	}

	@Override
	public Brand save(Brand entity) {
		return brandRepository.save(entity);
	}

	@Override
	public List<Brand> findAll() {
		return brandRepository.findAll();
	}

	@Override
	public Optional<Brand> findById(Integer id) {
		return brandRepository.findById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return brandRepository.existsById(id);
	}

	@Override
	public long count() {
		return brandRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		brandRepository.deleteById(id);
	}

	@Override
	public void delete(Brand entity) {
		brandRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		brandRepository.deleteAll();
	}

	@Override
	public Brand getById(Integer id) {
		return brandRepository.getById(id);
	}
	
	

}
