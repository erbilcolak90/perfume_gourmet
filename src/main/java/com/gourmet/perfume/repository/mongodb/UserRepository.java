package com.gourmet.perfume.repository.mongodb;

import com.gourmet.perfume.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
