package com.gourmet.perfume.service;

import com.gourmet.perfume.dto.input.favorite.AddPerfumeToFavoritesInput;
import com.gourmet.perfume.dto.input.favorite.GetAllFavoritesByUserIdInput;
import com.gourmet.perfume.dto.payload.favorite.FavoritePayload;
import com.gourmet.perfume.dto.payload.perfume.PerfumePayload;
import com.gourmet.perfume.dto.payload.user.UserPayload;
import com.gourmet.perfume.entity.Favorite;
import com.gourmet.perfume.exception.CustomException;
import com.gourmet.perfume.repository.elasticsearch.FavoriteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public FavoritePayload getFavoriteById(String id){
        return FavoritePayload.convert(favoriteRepository.findById(id).orElseThrow(()->CustomException.favoriteNotFound(id)));
    }

    public Page<FavoritePayload> getAllFavoritesByUserId(GetAllFavoritesByUserIdInput getAllFavoritesByUserIdInput){

        UserPayload dbUser = userService.getUserById(getAllFavoritesByUserIdInput.getUserId());
        Pageable pageable = getAllFavoritesByUserIdInput.toPageable();

        Page<Favorite> favoritePage = favoriteRepository.findAllByUserId(dbUser.getId(), pageable);

        return favoritePage.map(FavoritePayload::convert);
    }

    public List<FavoritePayload> getTopFavorites(){
        List<Favorite> favoriteList =  favoriteRepository.getTopFavorites();

        return favoriteList.stream().map(FavoritePayload::convert).toList();
    }

    @Transactional
    public FavoritePayload addPerfumeToFavorites(AddPerfumeToFavoritesInput addPerfumeToFavoritesInput){
        UserPayload dbUser = userService.getUserById(addPerfumeToFavoritesInput.getUserId());
        PerfumePayload dbPerfume = perfumeService.getPerfumeById(addPerfumeToFavoritesInput.getPerfumeId());

        Favorite favorite = new Favorite(null,dbUser.getId(), dbPerfume.getId());

        favoriteRepository.save(favorite);

        return FavoritePayload.convert(favorite);

    }
}
