package com.gourmet.perfume.service;

import com.gourmet.perfume.dto.input.user.ChangeUsernameInput;
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

import java.time.LocalDateTime;

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

    @Transactional
    public UserPayload changeUsername(ChangeUsernameInput changeUsernameInput){

        User dbUser = userRepository.findById(changeUsernameInput.getUserId().toLowerCase()).orElseThrow(()-> CustomException.userNotFound(changeUsernameInput.getUserId()));
        User  existUsername= userRepository.findByUsername(changeUsernameInput.getUsername().toLowerCase()).orElse(null);

        if(existUsername == null){
            dbUser.setUsername(changeUsernameInput.getUsername().toLowerCase());
            dbUser.setUpdateDate(LocalDateTime.now());

            userRepository.save(dbUser);

            return UserPayload.convert(dbUser);
        }else{
            throw CustomException.usernameIsAlreadyExist(changeUsernameInput.getUsername().toLowerCase());
        }
    }

    @Transactional
    public String deleteUser(String id){
        User dbUser = userRepository.findById(id).orElseThrow(()-> CustomException.userNotFound(id));

        if(dbUser.isDeleted()){
            throw CustomException.userIsAlreadyDeleted(id);
        }else {
            dbUser.setDeleted(true);
            dbUser.setUpdateDate(LocalDateTime.now());
            userRepository.save(dbUser);

            return "User id : " + id + " deleted";
        }
    }
}
