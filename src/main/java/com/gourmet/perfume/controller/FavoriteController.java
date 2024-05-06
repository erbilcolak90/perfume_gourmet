package com.gourmet.perfume.controller;

import com.gourmet.perfume.dto.input.favorite.AddPerfumeToFavoritesInput;
import com.gourmet.perfume.dto.input.favorite.GetAllFavoritesByUserIdInput;
import com.gourmet.perfume.dto.input.favorite.RemovePerfumeFromFavoritesInput;
import com.gourmet.perfume.dto.payload.favorite.FavoritePayload;
import com.gourmet.perfume.service.FavoriteService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping("/getFavoriteById")
    public ResponseEntity<FavoritePayload> getFavoriteById(@RequestParam String id){
        return ResponseEntity.ok(favoriteService.getFavoriteById(id));
    }

    @PostMapping("/getAllFavoritesByUserId")
    public ResponseEntity<Page<FavoritePayload>> getAllFavoritesByUserId(@RequestBody GetAllFavoritesByUserIdInput getAllFavoritesByUserIdInput){
        return ResponseEntity.ok(favoriteService.getAllFavoritesByUserId(getAllFavoritesByUserIdInput));
    }

    @GetMapping("/getTopFavorites")
    public ResponseEntity<List<FavoritePayload>> getTopFavorites(){
        return ResponseEntity.ok(favoriteService.getTopFavorites());
    }

    @PutMapping("/addPerfumeToFavorites")
    public ResponseEntity<FavoritePayload> addPerfumeToFavorites(@RequestBody AddPerfumeToFavoritesInput addPerfumeToFavoritesInput){
        return ResponseEntity.ok(favoriteService.addPerfumeToFavorites(addPerfumeToFavoritesInput));
    }

    @DeleteMapping("/removePerfumeFromFavorites")
    public ResponseEntity<Boolean> removePerfumeFromFavorites(@RequestBody RemovePerfumeFromFavoritesInput removePerfumeFromFavoritesInput){
        return ResponseEntity.ok(favoriteService.removePerfumeFromFavorites(removePerfumeFromFavoritesInput));
    }
}
