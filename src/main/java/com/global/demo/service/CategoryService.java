// src/main/java/com/global/demo/service/CategoryService.java
package com.global.demo.service;

import com.global.demo.entity.Category;
import com.global.demo.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepository;

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.orElse(null);
    }
//
//    public Category updateCategory(Long id, Category category) {
//        if (categoryRepository.existsById(id)) {
//            category.setId(id);
//            return categoryRepository.save(category);
//        }
//        return category;
//    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}