package com.gourmet.perfume;

import com.gourmet.perfume.entity.User;
import com.gourmet.perfume.repository.mongodb.UserRepository;
import com.gourmet.perfume.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userServiceMock;

    @Mock
    private UserRepository userRepositoryMock;

    private User userMock;

    @BeforeEach
    void setUp(){

    }

    @AfterEach
    void tearDown(){

    }

}
