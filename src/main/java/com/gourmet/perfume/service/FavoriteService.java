package com.gourmet.perfume.service;

import com.gourmet.perfume.repository.elasticsearch.FavoriteRepository;
import org.springframework.stereotype.Service;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    private final UserService userService;
    private final PerfumeService perfumeService;

    public FavoriteService(FavoriteRepository favoriteRepository, UserService userService, PerfumeService perfumeService) {
        this.favoriteRepository = favoriteRepository;
        this.userService = userService;
        this.perfumeService = perfumeService;
    }
}
