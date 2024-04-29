package com.gourmet.perfume.repository.mongodb;

import com.gourmet.perfume.entity.Perfume;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PerfumeRepository extends MongoRepository<Perfume, String> {
    Optional<Perfume> findByName(String name);

    @Query(value = "{'brand' : ?0}")
    List<Perfume> findByBrandName(String brandName);

    @Query("{'year' : { $gte: ?0, $lte: ?1 } }")
    List<Perfume> getPerfumeByYearRange(int from, int to);
}
