package com.gourmet.perfume;

import com.gourmet.perfume.entity.Perfume;
import com.gourmet.perfume.enums.TypeEnums;
import com.gourmet.perfume.exception.CustomException;
import com.gourmet.perfume.repository.mongodb.PerfumeRepository;
import com.gourmet.perfume.service.PerfumeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PerfumeServiceTest {

    @InjectMocks
    private PerfumeService perfumeServiceMock;

    @Mock
    private PerfumeRepository perfumeRepositoryMock;

    private Perfume perfumeMock;

    @BeforeEach
    void setUp() {
        perfumeMock = new Perfume(
                "test_id",
                "test_perfume_name",
                "test_brand_name",
                2023,
                new ArrayList<>(),
                TypeEnums.EAU_DE_PERFUME,
                "test_content",
                "test_description",
                new ArrayList<>());
    }

    @DisplayName("getPerfumeById should return perfumePayload when given id is exist")
    @Test
    void testGetPerfumeById_success() {
        String id = "test_id";
        when(perfumeRepositoryMock.findById(id)).thenReturn(Optional.ofNullable(perfumeMock));

        assertNotNull(perfumeServiceMock.getPerfumeById(id));
    }

    @DisplayName("getPerfumeById should throw custom exception perfumeNotFound when given id does not exist")
    @Test
    void testGetPerfumeById_perfumeNotFound() {
        String id = "test_id";
        when(perfumeRepositoryMock.findById(id)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> perfumeServiceMock.getPerfumeById(id));
    }

    @DisplayName("getPerfumeByName should return perfumePayload when given name is exist")
    @Test
    void testGetPerfumeByName_success() {
        String name = "test_name";
        when(perfumeRepositoryMock.findByName(name)).thenReturn(Optional.ofNullable(perfumeMock));

        assertNotNull(perfumeServiceMock.getPerfumeByName(name));
    }

    @DisplayName("getPerfumeByName should throw custom exception perfumeNameNotFound when given name does not exist")
    @Test
    void testGetPerfumeByName_perfumeNameNotFound() {
        String name = "test_name";
        when(perfumeRepositoryMock.findByName(name)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> perfumeServiceMock.getPerfumeByName(name));
    }

    @DisplayName("getPerfumesByBrandName should return list perfumePayload with given brandName and list size min. 1")
    @Test
    void testGetPerfumesByBrandName_success() {
        String brandName = "test_brand_name";
        List<Perfume> perfumeList = Arrays.asList(perfumeMock);

        when(perfumeRepositoryMock.findByBrandName(brandName)).thenReturn(perfumeList);

        assertEquals(1, perfumeServiceMock.getPerfumesByBrandName(brandName).size());
    }

    @DisplayName("getPerfumesByBrandName should throw custom exception noPerfumeBelongingThisBrand with given brandName")
    @Test
    void testGetPerfumesByBrandName_noPerfumeBelongingThisBrand() {
        String brandName = "test_brand_name";

        when(perfumeRepositoryMock.findByBrandName(brandName)).thenReturn(new ArrayList<>());

        assertThrows(CustomException.class, () -> perfumeServiceMock.getPerfumesByBrandName(brandName));
    }
    @AfterEach
    void tearDown() {

    }
}
