package dupradosantini.achievementsystem.services;

import dupradosantini.achievementsystem.domain.Game;
import dupradosantini.achievementsystem.repositories.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

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
        Game game1 = new Game();
        game1.setId(GAME_ID);
        List<Game> list = Arrays.asList(game1);

        when(gameRepository.findAll()).thenReturn(list);

        List<Game> listReturned = gameService.findAll();
        assertNotNull(listReturned,"Null list returned, expected something");
        assertFalse(listReturned.isEmpty(),"List of games shouldn't be empty");
    }
}