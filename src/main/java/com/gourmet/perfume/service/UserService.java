package com.gourmet.perfume.service;

import com.gourmet.perfume.dto.input.user.CreateUserInput;
import com.gourmet.perfume.dto.input.user.GetAllUsersInput;
import com.gourmet.perfume.dto.payload.user.UserPayload;
import com.gourmet.perfume.entity.User;
import com.gourmet.perfume.exception.CustomException;
import com.gourmet.perfume.repository.mongodb.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserPayload getUserById(String id){
        return UserPayload.convert(userRepository.findById(id).orElseThrow(()->CustomException.userNotFound(id)));
    }

    public UserPayload getUserByUsername(String username){
        return UserPayload.convert(userRepository.findByUsername(username.toLowerCase()).orElseThrow(()->CustomException.usernameNotFound(username)));

    }
  
      public Page<User> getAllUsers(GetAllUsersInput getAllUsersInput){
        Pageable pageable = getAllUsersInput.toPageable();

        return userRepository.findAll(pageable);
    }

    @Transactional
    public UserPayload createUser(CreateUserInput createUserInput){
        User dbUser = userRepository.findByUsername(createUserInput.getUsername().toLowerCase()).orElse(null);

        if(dbUser != null){
            throw CustomException.usernameIsAlreadyExist(dbUser.getUsername());
        }else{
            dbUser = new User(null, createUserInput.getUsername().toLowerCase(),createUserInput.getPassword(),createUserInput.getGender(),null );

            return UserPayload.convert(dbUser);
        }
    }
}
