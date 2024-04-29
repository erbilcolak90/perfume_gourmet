package com.gourmet.perfume.controller;

import com.gourmet.perfume.dto.input.perfume.*;
import com.gourmet.perfume.dto.payload.perfume.PerfumePayload;
import com.gourmet.perfume.service.PerfumeService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perfume")
public class PerfumeController {

    private final PerfumeService perfumeService;

    public PerfumeController(PerfumeService perfumeService) {
        this.perfumeService = perfumeService;
    }

    @GetMapping("/getPerfumeById")
    public ResponseEntity<PerfumePayload> getPerfumeById(@RequestParam String id) {
        return ResponseEntity.ok(perfumeService.getPerfumeById(id));
    }

    @GetMapping("/getPerfumeByName")
    public ResponseEntity<PerfumePayload> getPerfumeByName(@RequestParam String name) {
        return ResponseEntity.ok(perfumeService.getPerfumeByName(name));
    }

    @GetMapping("/getPerfumesByBrandName")
    public ResponseEntity<List<PerfumePayload>> getPerfumesByBrandName(@RequestParam String name) {
        return ResponseEntity.ok(perfumeService.getPerfumesByBrandName(name));
    }

    @GetMapping("/getPerfumeById")
    public ResponseEntity<List<PerfumePayload>> getPerfumesByYearRange(@RequestBody GetPerfumesByYearRangeInput getPerfumesByYearRangeInput) {
        return ResponseEntity.ok(perfumeService.getPerfumesByYearRange(getPerfumesByYearRangeInput));
    }

    @GetMapping("/getPerfumeById")
    public ResponseEntity<Page<PerfumePayload>> getAllPerfumes(@RequestBody GetAllPerfumesInput getAllPerfumesInput) {
        return ResponseEntity.ok(perfumeService.getAllPerfumes(getAllPerfumesInput));
    }

    @PostMapping("/addPerfume")
    public ResponseEntity<PerfumePayload> addPerfume(@RequestBody AddPerfumeInput addPerfumeInput) {
        return ResponseEntity.ok(perfumeService.addPerfume(addPerfumeInput));
    }

    @PutMapping("/updatePerfumeName")
    public ResponseEntity<PerfumePayload> updatePerfumeName(@RequestParam String id, @RequestParam String newName) {
        return ResponseEntity.ok(perfumeService.updatePerfumeName(id, newName));
    }

    @PutMapping("/updatePerfumeBrand")
    public ResponseEntity<PerfumePayload> updatePerfumeBrand(@RequestParam String id, @RequestParam String newBrand) {
        return ResponseEntity.ok(perfumeService.updatePerfumeBrand(id, newBrand));
    }

    @PutMapping("/updatePerfumeYear")
    public ResponseEntity<PerfumePayload> updatePerfumeYear(@RequestParam String id, @RequestParam int year) {
        return ResponseEntity.ok(perfumeService.updatePerfumeYear(id, year));
    }

    @PutMapping("/updatePerfumeContent")
    public ResponseEntity<PerfumePayload> updatePerfumeContent(@RequestBody UpdatePerfumeContentInput updatePerfumeContentInput) {
        return ResponseEntity.ok(perfumeService.updatePerfumeContent(updatePerfumeContentInput));
    }

    @PutMapping("/updatePerfumeDescription")
    public ResponseEntity<PerfumePayload> updatePerfumeDescription(@RequestBody UpdatePerfumeDescriptionInput updatePerfumeDescriptionInput) {
        return ResponseEntity.ok(perfumeService.updatePerfumeDescription(updatePerfumeDescriptionInput));
    }

    @PutMapping("/addCategoryToPerfume")
    public ResponseEntity<PerfumePayload> addCategoryToPerfume(@RequestBody AddCategoryToPerfumeInput addCategoryToPerfumeInput) {
        return ResponseEntity.ok(perfumeService.addCategoryToPerfume(addCategoryToPerfumeInput));
    }

    @PutMapping("/removeCategoryFromPerfume")
    public ResponseEntity<PerfumePayload> removeCategoryFromPerfume(@RequestBody RemoveCategoryFromPerfumeInput removeCategoryFromPerfumeInput) {
        return ResponseEntity.ok(perfumeService.removeCategoryFromPerfume(removeCategoryFromPerfumeInput));
    }


}
