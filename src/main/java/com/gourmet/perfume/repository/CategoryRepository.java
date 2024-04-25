package com.gourmet.perfume.repository;

import com.gourmet.perfume.entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
}