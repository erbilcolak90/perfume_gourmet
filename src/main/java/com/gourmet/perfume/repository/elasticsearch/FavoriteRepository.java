package com.gourmet.perfume.repository.elasticsearch;

import com.gourmet.perfume.entity.Favorite;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface FavoriteRepository extends ElasticsearchRepository<Favorite, String> {
}
