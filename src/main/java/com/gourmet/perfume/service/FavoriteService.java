package com.gourmet.perfume.service;

import com.gourmet.perfume.dto.payload.user.UserPayload;
import com.gourmet.perfume.entity.Favorite;
import com.gourmet.perfume.entity.User;
import com.gourmet.perfume.exception.CustomException;
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

    public Favorite getFavoriteById(String id){
        return favoriteRepository.findById(id).orElseThrow(()->CustomException.favoriteNotFound(id));
    }
}
