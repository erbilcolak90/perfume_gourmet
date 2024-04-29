package com.gourmet.perfume;

import com.gourmet.perfume.dto.input.user.GetAllUsersInput;
import com.gourmet.perfume.entity.User;
import com.gourmet.perfume.enums.GenderEnums;
import com.gourmet.perfume.exception.CustomException;
import com.gourmet.perfume.repository.mongodb.UserRepository;
import com.gourmet.perfume.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userServiceMock;

    @Mock
    private UserRepository userRepositoryMock;

    private User userMock;

    @BeforeEach
    void setUp() {
        userMock = new User(
                "test_id",
                "test_username",
                "test_password",
                GenderEnums.UNISEX,
                "test_avatar_id"
        );
    }

    @DisplayName("getUserById should return userPayload when given id is exist")
    @Test
    void testGetUserById_success() {
        String id = "test_id";

        when(userRepositoryMock.findById(id)).thenReturn(Optional.ofNullable(userMock));

        assertEquals(id, userServiceMock.getUserById(id).getId());
    }

    @DisplayName("getUserById should throw custom exception userNotFound when given id does not exist")
    @Test
    void testGetUserById_userNotFound() {
        String id = "test_id";

        when(userRepositoryMock.findById(id)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> userServiceMock.getUserById(id));
    }

    @DisplayName("getUserByUsername should return userPayload when given username is exist")
    @Test
    void testGetUserByUsername_success() {
        String username = "test_username";

        when(userRepositoryMock.findByUsername(username)).thenReturn(Optional.ofNullable(userMock));

        assertEquals(username, userServiceMock.getUserByUsername(username).getUsername());
    }

    @DisplayName("getUserByUsername should throw custom exception usernameNotFound when given username does not exist")
    @Test
    void testGetUserByUsername_usernameNotFound() {
        String username = "test_username";

        when(userRepositoryMock.findByUsername(username.toLowerCase())).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> userServiceMock.getUserByUsername(username));
    }
  
    @DisplayName("getAllUsers should return page user with given getAllUsersInput")
    @Test
    void testGetAllUsers_success(){
        GetAllUsersInput getAllUsersInput = new GetAllUsersInput(1,1,"username", Sort.Direction.ASC);
        Pageable pageable = getAllUsersInput.toPageable();
        PageImpl<User> userPage = new PageImpl<>(Arrays.asList(userMock));

        when(userRepositoryMock.findAll(pageable)).thenReturn(userPage);

        assertEquals(1, userServiceMock.getAllUsers(getAllUsersInput).getSize());
    }
    @AfterEach
    void tearDown() {

    }

}
