package com.gourmet.perfume.service;

import com.gourmet.perfume.dto.input.category.CreateCategoryInput;
import com.gourmet.perfume.dto.input.category.UpdateCategoryGenderInput;
import com.gourmet.perfume.dto.payload.category.CategoryPayload;
import com.gourmet.perfume.entity.Category;
import com.gourmet.perfume.exception.CustomException;
import com.gourmet.perfume.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryPayload getCategoryById(String id) {
        return CategoryPayload.convert(categoryRepository.findById(id).orElseThrow(() -> CustomException.categoryNotFound(id)));
    }

    public CategoryPayload getCategoryByName(String name) {
        return CategoryPayload.convert(categoryRepository.findByName(name).orElseThrow(() -> CustomException.categoryNameNotFound(name)));
    }

    public List<CategoryPayload> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(CategoryPayload::convert).toList();
    }

    @Transactional
    public CategoryPayload createCategory(CreateCategoryInput createCategoryInput) {

        Category dbCategory = categoryRepository.findByName(createCategoryInput.getName().toLowerCase()).orElse(null);

        if (dbCategory == null) {
            dbCategory = new Category("", createCategoryInput.getName().toLowerCase(), createCategoryInput.getGender());
            categoryRepository.save(dbCategory);
            return CategoryPayload.convert(dbCategory);
        } else {
            throw CustomException.categoryNameIsAlreadyExist(createCategoryInput.getName());
        }
    }

    @Transactional
    public CategoryPayload updateCategoryGender(UpdateCategoryGenderInput updateCategoryGenderInput) {
        String id = updateCategoryGenderInput.getId();
        Category dbCategory = categoryRepository.findById(id).orElseThrow(() -> CustomException.categoryNotFound(id));

        if (dbCategory.getGender().equals(updateCategoryGenderInput.getGender())) {
            throw CustomException.categoryGenderIsAlreadySameWithInputGender();
        } else {
            dbCategory.setGender(updateCategoryGenderInput.getGender());
            dbCategory.setUpdateDate(LocalDateTime.now());
            categoryRepository.save(dbCategory);

            return CategoryPayload.convert(dbCategory);
        }
    }

    @Transactional
    public boolean deleteCategory(String id) {
        Category dbCategory = categoryRepository.findById(id).orElseThrow(() -> CustomException.categoryNotFound(id));

        if (!dbCategory.isDeleted()) {
            dbCategory.setDeleted(true);
            dbCategory.setUpdateDate(LocalDateTime.now());
            categoryRepository.save(dbCategory);

            return true;
        } else {
            throw CustomException.categoryIsAlreadyDeleted(id);
        }
    }
}
