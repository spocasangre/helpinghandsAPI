package com.taquitosncapas.helpinghands.services.implementation;

import com.taquitosncapas.helpinghands.models.entities.Category;
import com.taquitosncapas.helpinghands.repositories.CategoryRepository;
import com.taquitosncapas.helpinghands.services.definition.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Iterable<Category> findAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public Optional<Category> findCategoryById(Long id) {
		return categoryRepository.findById(id);
	}

	@Override
	public Category saveCategory(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public void deleteCategoryById(Long id) {

	}
}
