package org.example.service;

import org.example.entity.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(Integer id);
    Category createCategory(Category category);
    Category updateCategory(Integer id, Category categoryDetails);
    void deleteCategory(Integer id);

    List<Category>findAllEnable();
}
