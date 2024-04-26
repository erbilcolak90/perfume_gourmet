package com.gourmet.perfume.service;

import com.gourmet.perfume.repository.mongodb.PerfumeRepository;
import org.springframework.stereotype.Service;

@Service
public class PerfumeService {

    private final PerfumeRepository perfumeRepository;

    public PerfumeService(PerfumeRepository perfumeRepository) {
        this.perfumeRepository = perfumeRepository;
    }
}
