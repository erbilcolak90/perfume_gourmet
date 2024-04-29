package com.gourmet.perfume;

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

    @AfterEach
    void tearDown() {

    }

}
