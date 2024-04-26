package com.gourmet.perfume.service;

import com.gourmet.perfume.dto.category.CategoryPayload;
import com.gourmet.perfume.entity.Category;
import com.gourmet.perfume.exception.CustomException;
import com.gourmet.perfume.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryPayload getCategoryById(String id) {
        return CategoryPayload.convert(categoryRepository.findById(id).orElseThrow(() -> CustomException.categoryNotFound(id)));
    }

    public CategoryPayload getCategoryByName(String name){
        return CategoryPayload.convert(categoryRepository.findByName(name).orElseThrow(()-> CustomException.categoryNameNotFound(name)));
    }
}
