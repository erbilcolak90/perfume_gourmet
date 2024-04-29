package com.gourmet.perfume.service;

import com.gourmet.perfume.dto.payload.user.UserPayload;
import com.gourmet.perfume.entity.User;
import com.gourmet.perfume.exception.CustomException;
import com.gourmet.perfume.repository.mongodb.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserPayload getUserById(String id){
        return UserPayload.convert(userRepository.findById(id).orElseThrow(()->CustomException.userNotFound(id)));
    }
}