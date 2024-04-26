package com.gourmet.perfume.repository.mongodb;

import com.gourmet.perfume.entity.Perfume;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PerfumeRepository extends MongoRepository<Perfume, String> {
    Optional<Perfume> findByName(String name);

    List<Perfume> findByBrandName(String brandName);
}
