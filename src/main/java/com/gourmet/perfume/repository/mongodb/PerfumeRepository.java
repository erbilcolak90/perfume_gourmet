package com.gourmet.perfume.repository.mongodb;

import com.gourmet.perfume.entity.Perfume;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PerfumeRepository extends MongoRepository<Perfume, String> {
}
