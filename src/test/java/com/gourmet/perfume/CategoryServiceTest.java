package com.gourmet.perfume;

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

    @AfterEach
    void tearDown() {
    }
}
