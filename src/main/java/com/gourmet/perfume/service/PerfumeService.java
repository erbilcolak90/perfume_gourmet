package com.gourmet.perfume.service;

import com.gourmet.perfume.dto.payload.perfume.PerfumePayload;
import com.gourmet.perfume.entity.Perfume;
import com.gourmet.perfume.exception.CustomException;
import com.gourmet.perfume.repository.mongodb.PerfumeRepository;
import org.springframework.stereotype.Service;

@Service
public class PerfumeService {

    private final PerfumeRepository perfumeRepository;

    public PerfumeService(PerfumeRepository perfumeRepository) {
        this.perfumeRepository = perfumeRepository;
    }

    public PerfumePayload getPerfumeById(String id){
        return PerfumePayload.convert(perfumeRepository.findById(id).orElseThrow(()-> CustomException.perfumeNotFound(id)));
    }
}
