package com.gourmet.perfume;

import com.gourmet.perfume.dto.input.favorite.GetAllFavoritesByUserIdInput;
import com.gourmet.perfume.dto.payload.user.UserPayload;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
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

    @DisplayName("getAllFavoritesByUserId should return page favorite when given userId is exist")
    @Test
    void testGetAllFavoritesByUserId_success(){
        GetAllFavoritesByUserIdInput getAllFavoritesByUserIdInput = new GetAllFavoritesByUserIdInput("test_user_id",1,1,"createDate", Sort.Direction.ASC);
        Pageable pageable = getAllFavoritesByUserIdInput.toPageable();
        UserPayload userPayload = new UserPayload("test_user_id","test_username","test_avatar_id");
        PageImpl<Favorite> favoritePage = new PageImpl<>(Arrays.asList(favoriteMock));

        when(userServiceMock.getUserById(getAllFavoritesByUserIdInput.getUserId())).thenReturn(userPayload);
        when(favoriteRepositoryMock.findAllByUserId(getAllFavoritesByUserIdInput.getUserId(), pageable)).thenReturn(favoritePage);

        assertEquals(1,favoriteServiceMock.getAllFavoritesByUserId(getAllFavoritesByUserIdInput).getSize());

    }

    @DisplayName("getTopFavorites should return list favorite")
    @Test
    void testGetTopFavorites_success(){

        when(favoriteRepositoryMock.getTopFavorites()).thenReturn(Arrays.asList(favoriteMock));

        assertEquals(1,favoriteServiceMock.getTopFavorites().size());
    }

    @AfterEach
    void tearDown(){

    }
}
