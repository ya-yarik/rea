package com.example.project.services;

import com.example.project.models.Category;
import com.example.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly=true)
public class CategoryServices {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServices(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Transactional
    public void newCategory (Category category){
        categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(int id){
        categoryRepository.deleteById(id);
    }

    @Transactional
    public Category getCategoryId(int id){
        Optional<Category> thatCategory = categoryRepository.findById(id);
        return thatCategory.orElse(null);
    }
    @Transactional
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
}