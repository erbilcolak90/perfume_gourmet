package com.gourmet.perfume;

import com.gourmet.perfume.entity.Favorite;
import com.gourmet.perfume.repository.elasticsearch.FavoriteRepository;
import com.gourmet.perfume.service.FavoriteService;
import com.gourmet.perfume.service.PerfumeService;
import com.gourmet.perfume.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FavoriteServiceTest {

    @InjectMocks
    private FavoriteService favoriteServiceMock;

    @Mock
    private FavoriteRepository favoriteRepositoryMock;

    @Mock
    private UserService userServiceMock;
    @Mock
    private PerfumeService perfumeServiceMock;

    private Favorite favoriteMock;

    @BeforeEach
    void setUp(){

    }

    @AfterEach
    void tearDown(){

    }
}
