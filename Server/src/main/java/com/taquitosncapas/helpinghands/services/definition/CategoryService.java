package com.taquitosncapas.helpinghands.services.definition;

import com.taquitosncapas.helpinghands.models.entities.Category;

import java.util.Optional;

public interface CategoryService {
	public Iterable<Category> findAllCategories();
	public Optional<Category> findCategoryById(Long id);
	public Category saveCategory(Category category);
	public void deleteCategoryById(Long id);
}
