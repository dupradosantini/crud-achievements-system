package dupradosantini.achievementsystem.services;

import dupradosantini.achievementsystem.domain.Game;
import dupradosantini.achievementsystem.repositories.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    private final Integer GAME_ID = 1;

    GameServiceImpl gameService;

    @Mock
    GameRepository gameRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(gameRepository);
        gameService = new GameServiceImpl(gameRepository);
    }

    @Test
    void findById() {
        Game game1 = new Game();
        game1.setId(GAME_ID);

        Optional<Game> gameOptional = Optional.of(game1);

        when(gameRepository.findById(anyInt())).thenReturn(gameOptional);

        Game gameReturned = gameService.findById(GAME_ID);

        assertNotNull(gameReturned,"Null game returned(expected something)");
        verify(gameRepository,times(1)).findById(anyInt());
    }

    @Test
    void findAll() {
        Page<Game> gamePage = Page.empty();
        Pageable paging = PageRequest.of(0,2);

        when(gameRepository.findAll(paging)).thenReturn(gamePage);

        Page<Game> pageReturned = gameService.findAll(paging);
        assertNotNull(pageReturned,"Null page returned, expected something");
        assertTrue(pageReturned.isEmpty(),"Page of games should be empty");
        verify(gameRepository,times(1)).findAll(paging);
    }
}