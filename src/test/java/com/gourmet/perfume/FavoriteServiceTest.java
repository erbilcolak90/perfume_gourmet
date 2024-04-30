package com.gourmet.perfume;

import com.gourmet.perfume.entity.Favorite;
import com.gourmet.perfume.exception.CustomException;
import com.gourmet.perfume.repository.elasticsearch.FavoriteRepository;
import com.gourmet.perfume.service.FavoriteService;
import com.gourmet.perfume.service.PerfumeService;
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

        favoriteMock = new Favorite("test_id","test_user_id","test_perfume_id");

    }

    @DisplayName("getFavoriteById should return favorite when given id is exist")
    @Test
    void testGetFavoriteById_success(){
        String id = "test_id";

        when(favoriteRepositoryMock.findById(id)).thenReturn(Optional.ofNullable(favoriteMock));

        assertEquals(id,favoriteServiceMock.getFavoriteById(id).getId());
    }

    @DisplayName("getFavoriteById should throw custom exception favoriteNotFound when given id does not exist")
    @Test
    void testGetFavoriteById_favoriteNotFound(){
        String id = "test_id";

        when(favoriteRepositoryMock.findById(id)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, ()-> favoriteServiceMock.getFavoriteById(id));
    }

    @AfterEach
    void tearDown(){

    }
}
