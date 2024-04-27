package com.gourmet.perfume.service;

import com.gourmet.perfume.dto.input.perfume.GetPerfumesByYearRangeInput;
import com.gourmet.perfume.dto.payload.perfume.PerfumePayload;
import com.gourmet.perfume.entity.Perfume;
import com.gourmet.perfume.exception.CustomException;
import com.gourmet.perfume.repository.mongodb.PerfumeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerfumeService {

    private final PerfumeRepository perfumeRepository;

    public PerfumeService(PerfumeRepository perfumeRepository) {
        this.perfumeRepository = perfumeRepository;
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

    public List<PerfumePayload> getPerfumesByYearRange(GetPerfumesByYearRangeInput getPerfumesByYearRangeInput){
        int from = getPerfumesByYearRangeInput.getFrom();
        int to = getPerfumesByYearRangeInput.getTo();

        if(from > to){
            int temp = from;
            from = to;
            to = temp;
        }
        List<Perfume> perfumeListByYearRange = perfumeRepository.getPerfumeByYearRange(from,to);

        if(perfumeListByYearRange.isEmpty()){
            throw CustomException.perfumeNotFoundBetweenTheseYears(from,to);
        }

        return perfumeListByYearRange.stream().map(PerfumePayload::convert).toList();
    }
}
