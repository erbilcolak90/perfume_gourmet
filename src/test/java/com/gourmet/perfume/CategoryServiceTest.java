package com.gourmet.perfume;

import com.gourmet.perfume.dto.input.category.CreateCategoryInput;
import com.gourmet.perfume.dto.payload.category.CategoryPayload;
import com.gourmet.perfume.entity.Category;
import com.gourmet.perfume.enums.GenderEnums;
import com.gourmet.perfume.exception.CustomException;
import com.gourmet.perfume.repository.CategoryRepository;
import com.gourmet.perfume.service.CategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryServiceMock;

    @Mock
    private CategoryRepository categoryRepositoryMock;

    private Category categoryMock;

    @BeforeEach
    void setUp() {
        categoryMock = new Category("test_category_id", "test_category_name", GenderEnums.MALE);
    }

    @DisplayName("getCategoryById should return categoryPayload when given id is exist on db")
    @Test
    void testGetCategoryById_success() {
        String id = "test_category_id";
        when(categoryRepositoryMock.findById(id)).thenReturn(Optional.ofNullable(categoryMock));

        assertNotNull(categoryServiceMock.getCategoryById(id));
    }

    @DisplayName("getCategoryById should throw custom exception categoryNotFound when given id does not exist on db")
    @Test
    void testGetCategoryById_categoryNotFound() {
        String id = "test_category_id";
        when(categoryRepositoryMock.findById(id)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> categoryServiceMock.getCategoryById(id));
    }

    @DisplayName("getCategoryByName should return categoryPayload when given name is exist")
    @Test
    void testGetCategoryByName_success() {
        String name = "test_category_name";
        when(categoryRepositoryMock.findByName(name)).thenReturn(Optional.ofNullable(categoryMock));

        assertNotNull(categoryServiceMock.getCategoryByName(name));
    }

    @DisplayName("getCategoryByName should throw custom exception categoryNameNotFound when given name does not exist")
    @Test
    void testGetCategoryByName_categoryNameNotFound() {
        String name = "test_category_name";
        when(categoryRepositoryMock.findByName(name)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> categoryServiceMock.getCategoryByName(name));
    }

    @DisplayName("getAllCategories should return list categoryPayload")
    @Test
    void testGetAllCategories_success(){
        List<Category> categories = new ArrayList<>();
        categories.add(categoryMock);

        when(categoryRepositoryMock.findAll()).thenReturn(categories);

        assertEquals(1,categoryServiceMock.getAllCategories().size());
    }

    @DisplayName("createCategory should return categoryPayload when given name does not exist in createCategoryInput")
    @Test
    void testCreateCategory_success(){
        CreateCategoryInput createCategoryInput = new CreateCategoryInput("test_category_name",GenderEnums.MALE);

        when(categoryRepositoryMock.findByName(createCategoryInput.getName().toLowerCase())).thenReturn(Optional.empty());
        when(categoryRepositoryMock.save(any(Category.class))).thenReturn(categoryMock);

        assertEquals("test_category_name",categoryServiceMock.createCategory(createCategoryInput).getName());

    }

    @DisplayName("createCategory should throw custom exception categoryNameIsAlreadyExist when given name is exist in createCategoryInput")
    @Test
    void testCreateCategory_categoryNameIsAlreadyExist(){
        CreateCategoryInput createCategoryInput = new CreateCategoryInput("test_category_name",GenderEnums.MALE);

        when(categoryRepositoryMock.findByName(createCategoryInput.getName().toLowerCase())).thenReturn(Optional.ofNullable(categoryMock));

        assertThrows(CustomException.class, ()-> categoryServiceMock.createCategory(createCategoryInput));
    }

    @AfterEach
    void tearDown() {
    }
}
