package com.gourmet.perfume;

import com.gourmet.perfume.dto.input.perfume.*;
import com.gourmet.perfume.dto.payload.category.CategoryPayload;
import com.gourmet.perfume.dto.payload.perfume.PerfumePayload;
import com.gourmet.perfume.entity.Perfume;
import com.gourmet.perfume.enums.GenderEnums;
import com.gourmet.perfume.enums.TypeEnums;
import com.gourmet.perfume.exception.CustomException;
import com.gourmet.perfume.repository.mongodb.PerfumeRepository;
import com.gourmet.perfume.service.CategoryService;
import com.gourmet.perfume.service.PerfumeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

    @Mock
    private CategoryService categoryService;

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

    @DisplayName("getPerfumesByYearRange should return list perfumePayload with given getPerfumesByYearRangeInput")
    @Test
    void testGetPerfumesByYearRange_success() {
        GetPerfumesByYearRangeInput getPerfumesByYearRangeInput = new GetPerfumesByYearRangeInput(2024, 2023);
        List<Perfume> perfumeList = Arrays.asList(perfumeMock);
        int from = getPerfumesByYearRangeInput.getFrom();
        int to = getPerfumesByYearRangeInput.getTo();

        if (from > to) {
            int temp = from;
            from = to;
            to = temp;
        }
        when(perfumeRepositoryMock.getPerfumeByYearRange(from, to)).thenReturn(perfumeList);


        assertEquals(1, perfumeServiceMock.getPerfumesByYearRange(getPerfumesByYearRangeInput).size());
    }

    @DisplayName("getPerfumesByYearRange should throw custom exception perfumeNotFoundBetweenTheseYears when perfumes not found between given input years")
    @Test
    void testGetPerfumesByYearRange_perfumeNotFoundBetweenTheseYears() {
        GetPerfumesByYearRangeInput getPerfumesByYearRangeInput = new GetPerfumesByYearRangeInput(2024, 2023);
        int from = getPerfumesByYearRangeInput.getFrom();
        int to = getPerfumesByYearRangeInput.getTo();

        if (from > to) {
            int temp = from;
            from = to;
            to = temp;
        }
        when(perfumeRepositoryMock.getPerfumeByYearRange(from, to)).thenReturn(Arrays.asList());

        assertThrows(CustomException.class, () -> perfumeServiceMock.getPerfumesByYearRange(getPerfumesByYearRangeInput));
    }

    @DisplayName("getAllPerfumes should return page perfumePayload with given getAllPerfumesInput")
    @Test
    void testGetAllPerfumes_success() {
        GetAllPerfumesInput getAllPerfumesInput = new GetAllPerfumesInput(1, 0, "brandName", Sort.Direction.ASC);
        Pageable pageable = getAllPerfumesInput.toPageable();

        PageImpl<Perfume> perfumePage = new PageImpl<>(Arrays.asList(perfumeMock));

        when(perfumeRepositoryMock.findAll(pageable)).thenReturn(perfumePage);

        Page<PerfumePayload> result = perfumeServiceMock.getAllPerfumes(getAllPerfumesInput);

        assertEquals(1, result.getSize());
    }

    @DisplayName("addPerfume should return perfumePayload when given name does not exist from addPerfumeInput")
    @Test
    void testAddPerfume_success() {
        AddPerfumeInput addPerfumeInput = new AddPerfumeInput("test_perfume_name", "test_brand_name", 2023, new ArrayList<>(), TypeEnums.EAU_DE_PERFUME, "test_content", "test_description", new ArrayList<>());

        when(perfumeRepositoryMock.findByName(addPerfumeInput.getName().toLowerCase())).thenReturn(Optional.empty());
        when(perfumeRepositoryMock.save(any(Perfume.class))).thenReturn(perfumeMock);

        assertEquals("test_perfume_name", perfumeServiceMock.addPerfume(addPerfumeInput).getName());
    }

    @DisplayName("addPerfume should throw custom exception perfumeNameIsAlreadyExist when given name is exist from addPerfumeInput")
    @Test
    void testAddPerfume_perfumeNameIsAlreadyExist() {
        AddPerfumeInput addPerfumeInput = new AddPerfumeInput("test_perfume_name", "test_brand_name", 2023, new ArrayList<>(), TypeEnums.EAU_DE_PERFUME, "test_content", "test_description", new ArrayList<>());

        when(perfumeRepositoryMock.findByName(addPerfumeInput.getName().toLowerCase())).thenReturn(Optional.ofNullable(perfumeMock));

        assertThrows(CustomException.class, () -> perfumeServiceMock.addPerfume(addPerfumeInput));
    }

    @DisplayName("updatePerfumeName should return perfumePayload when given id is exist given name does not exist ")
    @Test
    void testUpdatePerfumeName_success() {
        String test_id= "test_id";
        String newName = "test_new_name";
        when(perfumeRepositoryMock.findById(test_id)).thenReturn(Optional.ofNullable(perfumeMock));
        when(perfumeRepositoryMock.findByName(newName)).thenReturn(Optional.empty());
        when(perfumeRepositoryMock.save(any(Perfume.class))).thenReturn(perfumeMock);

        assertEquals(newName, perfumeServiceMock.updatePerfumeName(test_id,newName).getName());
    }

    @DisplayName("updatePerfumeName should throw custom exception perfumeNotFound when given id does not exist ")
    @Test
    void testUpdatePerfumeName_perfumeNotFound() {

        when(perfumeRepositoryMock.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(CustomException.class, ()-> perfumeServiceMock.updatePerfumeName("",""));
    }

    @DisplayName("updatePerfumeName should throw custom exception perfumeNameIsAlreadyExist when given id is exist and name is exist ")
    @Test
    void testUpdatePerfumeName_perfumeNameIsAlreadyExist() {

        when(perfumeRepositoryMock.findById(anyString())).thenReturn(Optional.ofNullable(perfumeMock));
        when(perfumeRepositoryMock.findByName(anyString())).thenReturn(Optional.ofNullable(perfumeMock));

        assertThrows(CustomException.class, ()-> perfumeServiceMock.updatePerfumeName("",""));
    }

    @DisplayName("updatePerfumeBrand should return perfumePayload when given id is exist")
    @Test
    void testUpdatePerfumeBrand_success(){
        String test_id =  "test_id";
        String newBrand= "new_brand";

        when(perfumeRepositoryMock.findById(test_id)).thenReturn(Optional.ofNullable(perfumeMock));
        when(perfumeRepositoryMock.save(perfumeMock)).thenReturn(perfumeMock);

        assertEquals(newBrand,perfumeServiceMock.updatePerfumeName(test_id,newBrand).getName());
    }

    @DisplayName("updatePerfumeBrand should throw custom exception perfumeNotFound when given id does not exist")
    @Test
    void testUpdatePerfumeBrand_perfumeNotFound(){
        String test_id =  "test_id";
        String newBrand= "new_brand";

        when(perfumeRepositoryMock.findById(test_id)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, ()-> perfumeServiceMock.updatePerfumeBrand(test_id,newBrand));
    }

    @DisplayName("updatePerfumeYear should return perfumePayload when given id is exist")
    @Test
    void testUpdatePerfumeYear_success(){
        String test_id =  "test_id";
        int newYear = 2025;

        when(perfumeRepositoryMock.findById(test_id)).thenReturn(Optional.ofNullable(perfumeMock));
        when(perfumeRepositoryMock.save(perfumeMock)).thenReturn(perfumeMock);

        assertEquals(newYear,perfumeServiceMock.updatePerfumeYear(test_id,newYear).getYear());
    }

    @DisplayName("updatePerfumeYear should throw custom exception perfumeNotFound when given id does not exist")
    @Test
    void testUpdatePerfumeYear_perfumeNotFound(){
        String test_id =  "test_id";
        int newYear = 2025;

        when(perfumeRepositoryMock.findById(test_id)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, ()-> perfumeServiceMock.updatePerfumeYear(test_id,newYear));
    }

    @DisplayName("updatePerfumeContent should return perfumePayload when given id is exist")
    @Test
    void testUpdatePerfumeContent_success(){
        UpdatePerfumeContentInput updatePerfumeContentInput = new UpdatePerfumeContentInput("test_id","test_new_content");

        when(perfumeRepositoryMock.findById(updatePerfumeContentInput.getId())).thenReturn(Optional.ofNullable(perfumeMock));
        when(perfumeRepositoryMock.save(perfumeMock)).thenReturn(perfumeMock);

        assertEquals(updatePerfumeContentInput.getContent(),perfumeServiceMock.updatePerfumeContent(updatePerfumeContentInput).getContent());
    }

    @DisplayName("updatePerfumeContent should throw custom exception perfumeNotFound when given id does not exist")
    @Test
    void testUpdatePerfumeContent_perfumeNotFound(){
        UpdatePerfumeContentInput updatePerfumeContentInput = new UpdatePerfumeContentInput("test_id","test_new_content");

        when(perfumeRepositoryMock.findById(updatePerfumeContentInput.getId())).thenReturn(Optional.empty());

        assertThrows(CustomException.class, ()-> perfumeServiceMock.updatePerfumeContent(updatePerfumeContentInput));
    }




    @DisplayName("updatePerfumeDescription should return perfumePayload when given id is exist")
    @Test
    void testUpdatePerfumeDescription_success(){
        UpdatePerfumeDescriptionInput updatePerfumeDescriptionInput = new UpdatePerfumeDescriptionInput("test_id","test_new_description");

        when(perfumeRepositoryMock.findById(updatePerfumeDescriptionInput.getId())).thenReturn(Optional.ofNullable(perfumeMock));
        when(perfumeRepositoryMock.save(perfumeMock)).thenReturn(perfumeMock);

        assertEquals(updatePerfumeDescriptionInput.getDescription(),perfumeServiceMock.updatePerfumeDescription(updatePerfumeDescriptionInput).getDescription());
    }

    @DisplayName("updatePerfumeDescription should throw custom exception perfumeNotFound when given id does not exist")
    @Test
    void testUpdatePerfumeDescription_perfumeNotFound(){
        UpdatePerfumeDescriptionInput updatePerfumeDescriptionInput = new UpdatePerfumeDescriptionInput("test_id","test_new_description");

        when(perfumeRepositoryMock.findById(updatePerfumeDescriptionInput.getId())).thenReturn(Optional.empty());

        assertThrows(CustomException.class, ()-> perfumeServiceMock.updatePerfumeDescription(updatePerfumeDescriptionInput));
    }

    @DisplayName("addCategoryToPerfume should return perfumePayload when given id is exist and exist perfume has not category id given category id from AddCategoryPerfumeInput")
    @Test
    void testAddCategoryToPerfume_success(){
        AddCategoryToPerfumeInput addCategoryToPerfumeInput = new AddCategoryToPerfumeInput("test_id","test_category_id");
        CategoryPayload categoryPayload = new CategoryPayload("test_category_id","test_name", GenderEnums.UNISEX);

        when(perfumeRepositoryMock.findById(addCategoryToPerfumeInput.getId())).thenReturn(Optional.ofNullable(perfumeMock));
        when(categoryService.getCategoryById(addCategoryToPerfumeInput.getCategoryId())).thenReturn(categoryPayload);

        assertEquals(1,perfumeServiceMock.addCategoryToPerfume(addCategoryToPerfumeInput).getCategoryIds().size());
    }

    @DisplayName("addCategoryToPerfume should throw custom exception perfumeNotFound when given id does not exist from AddCategoryToPerfumeInput")
    @Test
    void testAddCategoryToPerfume_perfumeNotFound(){
        AddCategoryToPerfumeInput addCategoryToPerfumeInput = new AddCategoryToPerfumeInput("test_id","test_category_id");

        when(perfumeRepositoryMock.findById(addCategoryToPerfumeInput.getId())).thenReturn(Optional.empty());

        assertThrows(CustomException.class, ()-> perfumeServiceMock.addCategoryToPerfume(addCategoryToPerfumeInput));
    }

    @DisplayName("addCategoryToPerfume should throw custom exception perfumeAlreadyExistOnCategory when given id does not exist from AddCategoryToPerfumeInput")
    @Test
    void testAddCategoryToPerfume_perfumeAlreadyExistOnCategory(){
        AddCategoryToPerfumeInput addCategoryToPerfumeInput = new AddCategoryToPerfumeInput("test_id","test_category_id");
        perfumeMock.getCategoryIds().add("test_category_id");
        CategoryPayload categoryPayload = new CategoryPayload("test_category_id","test_name", GenderEnums.UNISEX);

        when(perfumeRepositoryMock.findById(addCategoryToPerfumeInput.getId())).thenReturn(Optional.ofNullable(perfumeMock));
        when(categoryService.getCategoryById(addCategoryToPerfumeInput.getCategoryId())).thenReturn(categoryPayload);

        assertThrows(CustomException.class, ()-> perfumeServiceMock.addCategoryToPerfume(addCategoryToPerfumeInput));
    }

    @AfterEach
    void tearDown() {

    }
}
