package com.gourmet.perfume.repository.elasticsearch;

import com.gourmet.perfume.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface FavoriteRepository extends ElasticsearchRepository<Favorite, String> {

    @Query("{\"bool\" : {\"must\" : {\"match\" : \"userId\" : \"?0\"}}}")
    Page<Favorite> findAllByUserId(String userId, Pageable pageable);
}
