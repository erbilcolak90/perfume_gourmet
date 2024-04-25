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
    void setUp(){
        categoryMock = new Category("test_category_id","test_category_name", GenderEnums.MALE);
    }

    @DisplayName("getCategoryById should return categoryPayload when given id is exist on db")
    @Test
    void testGetCategoryById_success(){
        String id = "test_category_id";
        when(categoryRepositoryMock.findById(id)).thenReturn(Optional.ofNullable(categoryMock));

        assertNotNull(categoryServiceMock.getCategoryById(id));
    }

    @DisplayName("getCategoryById should throw custom exception categoryNotFound when given id does not exist on db")
    @Test
    void testGetCategoryById_categoryNotFound(){
        String id = "test_category_id";
        when(categoryRepositoryMock.findById(id)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, ()-> categoryServiceMock.getCategoryById(id));
    }

    @AfterEach
    void tearDown(){
    }
}
