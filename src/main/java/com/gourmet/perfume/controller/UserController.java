package com.gourmet.perfume.controller;

import com.gourmet.perfume.dto.input.user.ChangeUsernameInput;
import com.gourmet.perfume.dto.input.user.CreateUserInput;
import com.gourmet.perfume.dto.input.user.GetAllUsersInput;
import com.gourmet.perfume.dto.payload.user.UserPayload;
import com.gourmet.perfume.entity.User;
import com.gourmet.perfume.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUserById")
    public ResponseEntity<UserPayload> getUserById(@RequestParam String id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/getUserByUsername")
    public ResponseEntity<UserPayload> getUserByUsername(@RequestParam String username){
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PostMapping("/getAllUsers")
    public ResponseEntity<Page<User>> getAllUsers(@RequestBody GetAllUsersInput getAllUsersInput){
        return ResponseEntity.ok(userService.getAllUsers(getAllUsersInput));
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserPayload> createUser(@RequestBody CreateUserInput createUserInput){
        return ResponseEntity.ok(userService.createUser(createUserInput));
    }

    @PutMapping("/changeUsername")
    public ResponseEntity<UserPayload> changeUsername(@RequestBody ChangeUsernameInput changeUsernameInput){
        return ResponseEntity.ok(userService.changeUsername(changeUsernameInput));
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam String id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
