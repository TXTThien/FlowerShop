package org.example.service.Impl;

import org.example.entity.Category;
import org.example.entity.enums.Status;
import org.example.repository.CategoryRepository;
import org.example.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Integer id, Category categoryDetails) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setCategoryName(categoryDetails.getCategoryName());
            category.setStatus(categoryDetails.getStatus());
            return categoryRepository.save(category);
        } else {
            return null; // hoặc ném ngoại lệ nếu muốn xử lý lỗi
        }
    }

    @Override
    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> findAllEnable() {
        return categoryRepository.findAllByStatus(Status.ENABLE);
    }
}