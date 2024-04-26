package com.gourmet.perfume.controller;

import com.gourmet.perfume.dto.input.category.CreateCategoryInput;
import com.gourmet.perfume.dto.input.category.UpdateCategoryGenderInput;
import com.gourmet.perfume.dto.payload.category.CategoryPayload;
import com.gourmet.perfume.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/getCategoryById")
    public ResponseEntity<CategoryPayload> getCategoryById(@RequestParam String id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @GetMapping("/getCategoryByName")
    public ResponseEntity<CategoryPayload> getCategoryByName(@RequestParam String name){
        return ResponseEntity.ok(categoryService.getCategoryByName(name));
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<List<CategoryPayload>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping("/createCategory")
    public ResponseEntity<CategoryPayload> createCategory(@RequestBody CreateCategoryInput createCategoryInput){
        return ResponseEntity.ok(categoryService.createCategory(createCategoryInput));
    }

    @PutMapping("/updateCategoryGender")
    public ResponseEntity<CategoryPayload> updateCategoryGender(@RequestBody UpdateCategoryGenderInput updateCategoryGenderInput){
        return ResponseEntity.ok(categoryService.updateCategoryGender(updateCategoryGenderInput));
    }

    @DeleteMapping("/deleteCategory")
    public ResponseEntity<Boolean> deleteCategory(@RequestParam String id){
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }
}
