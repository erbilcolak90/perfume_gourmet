package com.gourmet.perfume;

import com.gourmet.perfume.entity.Category;
import com.gourmet.perfume.enums.GenderEnums;
import com.gourmet.perfume.repository.CategoryRepository;
import com.gourmet.perfume.service.CategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @AfterEach
    void tearDown(){
    }
}
