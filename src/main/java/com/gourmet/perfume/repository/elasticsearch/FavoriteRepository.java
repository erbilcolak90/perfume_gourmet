package com.gourmet.perfume.repository.elasticsearch;

import com.gourmet.perfume.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends ElasticsearchRepository<Favorite, String> {

    @Query("{\"bool\" : {\"must\" : {\"match\" : \"userId\" : \"?0\"}}}")
    Page<Favorite> findAllByUserId(String userId, Pageable pageable);

    @Query("{\"aggs\":{\"top_perfumes\":{\"terms\":{\"field\":\"perfumeId.keyword\",\"size\":10}}},\"size\":0}")
    List<Favorite> getTopFavorites();

    @Query("{\"bool\": {\"must\": [{\"term\": {\"userId\": \"?0\"}}, {\"term\": {\"perfumeId\": \"?1\"}}]}}")
    Optional<Favorite> getFavoriteByUserIdAndPerfumeId(String userId, String perfumeId);
}
