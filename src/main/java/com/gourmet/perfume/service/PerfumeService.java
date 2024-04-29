package com.gourmet.perfume.service;

import com.gourmet.perfume.dto.input.perfume.*;
import com.gourmet.perfume.dto.payload.category.CategoryPayload;
import com.gourmet.perfume.dto.payload.perfume.PerfumePayload;
import com.gourmet.perfume.entity.Category;
import com.gourmet.perfume.entity.Perfume;
import com.gourmet.perfume.exception.CustomException;
import com.gourmet.perfume.repository.mongodb.PerfumeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PerfumeService {

    private final PerfumeRepository perfumeRepository;
    private final CategoryService categoryService;

    public PerfumeService(PerfumeRepository perfumeRepository, CategoryService categoryService) {
        this.perfumeRepository = perfumeRepository;
        this.categoryService = categoryService;
    }

    public PerfumePayload getPerfumeById(String id) {
        return PerfumePayload.convert(perfumeRepository.findById(id).orElseThrow(() -> CustomException.perfumeNotFound(id)));
    }

    public PerfumePayload getPerfumeByName(String name) {
        return PerfumePayload.convert(perfumeRepository.findByName(name).orElseThrow(() -> CustomException.perfumeNameNotFound(name)));
    }

    public List<PerfumePayload> getPerfumesByBrandName(String brandName) {
        List<Perfume> perfumeList = perfumeRepository.findByBrandName(brandName);

        List<PerfumePayload> perfumePayloads = perfumeList.stream().map(PerfumePayload::convert).toList();

        if (perfumePayloads.isEmpty()) {
            throw CustomException.noPerfumeBelongingThisBrand(brandName);
        }

        return perfumePayloads;
    }

    public List<PerfumePayload> getPerfumesByYearRange(GetPerfumesByYearRangeInput getPerfumesByYearRangeInput) {
        int from = getPerfumesByYearRangeInput.getFrom();
        int to = getPerfumesByYearRangeInput.getTo();

        if (from > to) {
            int temp = from;
            from = to;
            to = temp;
        }
        List<Perfume> perfumeListByYearRange = perfumeRepository.getPerfumeByYearRange(from, to);

        if (perfumeListByYearRange.isEmpty()) {
            throw CustomException.perfumeNotFoundBetweenTheseYears(from, to);
        }

        return perfumeListByYearRange.stream().map(PerfumePayload::convert).toList();
    }

    public Page<PerfumePayload> getAllPerfumes(GetAllPerfumesInput getAllPerfumesInput) {
        Pageable pageable = getAllPerfumesInput.toPageable();

        Page<Perfume> perfumePage = perfumeRepository.findAll(pageable);

        return perfumePage.map(PerfumePayload::convert);

    }

    @Transactional
    public PerfumePayload addPerfume(AddPerfumeInput addPerfumeInput) {

        Perfume dbPerfume = perfumeRepository.findByName(addPerfumeInput.getName().toLowerCase()).orElse(null);

        if (dbPerfume == null) {

            dbPerfume = new Perfume(
                    null, addPerfumeInput.getName().toLowerCase(),
                    addPerfumeInput.getBrand().toLowerCase(),
                    addPerfumeInput.getYear(),
                    addPerfumeInput.getCategoryIds(),
                    addPerfumeInput.getType(),
                    addPerfumeInput.getContent().toLowerCase(),
                    addPerfumeInput.getDescription().toLowerCase(),
                    addPerfumeInput.getThumbnailIds()
            );
            perfumeRepository.save(dbPerfume);

            return PerfumePayload.convert(dbPerfume);
        } else {
            throw CustomException.perfumeNameIsAlreadyExist(dbPerfume.getName());
        }
    }

    @Transactional
    public PerfumePayload updatePerfumeName(String id, String newName) {
        Perfume dbPerfume = perfumeRepository.findById(id).orElseThrow(() -> CustomException.perfumeNotFound(id));

        Perfume isExistName = perfumeRepository.findByName(newName.toLowerCase()).orElse(null);

        if (isExistName == null) {
            dbPerfume.setName(newName.toLowerCase());
            dbPerfume.setUpdateDate(LocalDateTime.now());

            perfumeRepository.save(dbPerfume);

            return PerfumePayload.convert(dbPerfume);
        } else {
            throw CustomException.perfumeNameIsAlreadyExist(newName);
        }
    }

    @Transactional
    public PerfumePayload updatePerfumeBrand(String id, String newBrand) {
        Perfume dbPerfume = perfumeRepository.findById(id).orElseThrow(() -> CustomException.perfumeNotFound(id));

        dbPerfume.setBrand(newBrand.toLowerCase());
        dbPerfume.setUpdateDate(LocalDateTime.now());

        perfumeRepository.save(dbPerfume);

        return PerfumePayload.convert(dbPerfume);

    }

    @Transactional
    public PerfumePayload updatePerfumeYear(String id, int year) {
        Perfume dbPerfume = perfumeRepository.findById(id).orElseThrow(() -> CustomException.perfumeNotFound(id));

        dbPerfume.setYear(year);
        dbPerfume.setUpdateDate(LocalDateTime.now());

        perfumeRepository.save(dbPerfume);

        return PerfumePayload.convert(dbPerfume);

    }

    @Transactional
    public PerfumePayload updatePerfumeContent(UpdatePerfumeContentInput updatePerfumeContentInput) {
        Perfume dbPerfume = perfumeRepository.findById(updatePerfumeContentInput.getId()).orElseThrow(() -> CustomException.perfumeNotFound(updatePerfumeContentInput.getId()));

        dbPerfume.setContent(updatePerfumeContentInput.getContent());
        dbPerfume.setUpdateDate(LocalDateTime.now());

        perfumeRepository.save(dbPerfume);

        return PerfumePayload.convert(dbPerfume);

    }

    @Transactional
    public PerfumePayload updatePerfumeDescription(UpdatePerfumeDescriptionInput updatePerfumeDescriptionInput) {
        Perfume dbPerfume = perfumeRepository.findById(updatePerfumeDescriptionInput.getId()).orElseThrow(() -> CustomException.perfumeNotFound(updatePerfumeDescriptionInput.getId()));

        dbPerfume.setDescription(updatePerfumeDescriptionInput.getDescription());
        dbPerfume.setUpdateDate(LocalDateTime.now());

        perfumeRepository.save(dbPerfume);

        return PerfumePayload.convert(dbPerfume);

    }

    @Transactional
    public PerfumePayload addCategoryToPerfume(AddCategoryToPerfumeInput addCategoryToPerfumeInput){
        Perfume dbPerfume = perfumeRepository.findById(addCategoryToPerfumeInput.getId()).orElseThrow(()-> CustomException.perfumeNotFound(addCategoryToPerfumeInput.getId()));
        CategoryPayload dbCategory = categoryService.getCategoryById(addCategoryToPerfumeInput.getCategoryId());

        if(!dbPerfume.getCategoryIds().contains(dbCategory.getId())){

            dbPerfume.getCategoryIds().add(dbCategory.getId());
            dbPerfume.setUpdateDate(LocalDateTime.now());

            perfumeRepository.save(dbPerfume);

            return PerfumePayload.convert(dbPerfume);
        }else{
            throw CustomException.perfumeAlreadyExistOnCategory(addCategoryToPerfumeInput.getCategoryId());
        }
    }

    @Transactional
    public PerfumePayload removeCategoryFromPerfume(RemoveCategoryFromPerfumeInput removeCategoryFromPerfumeInput){
        Perfume dbPerfume = perfumeRepository.findById(removeCategoryFromPerfumeInput.getId()).orElseThrow(()-> CustomException.perfumeNotFound(removeCategoryFromPerfumeInput.getId()));
        CategoryPayload dbCategory = categoryService.getCategoryById(removeCategoryFromPerfumeInput.getCategoryId());

        if(dbPerfume.getCategoryIds().contains(dbCategory.getId())){
            dbPerfume.getCategoryIds().remove(dbCategory.getId());
            dbPerfume.setUpdateDate(LocalDateTime.now());

            perfumeRepository.save(dbPerfume);

            return PerfumePayload.convert(dbPerfume);
        }else{
            throw CustomException.perfumeCategoryListNotContainsInputCategory(dbCategory.getId());
        }
    }
}
