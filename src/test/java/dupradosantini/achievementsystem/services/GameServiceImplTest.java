package dupradosantini.achievementsystem.services;

import dupradosantini.achievementsystem.domain.Achievement;
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


import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    private final Integer GAME_ID = 1;

    GameServiceImpl gameService;

    @Mock
    AchievementServiceImpl achievementService;

    @Mock
    GameRepository gameRepository;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(achievementService);

        MockitoAnnotations.openMocks(gameRepository);
        gameService = new GameServiceImpl(gameRepository, achievementService);
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
    @Test
    void create(){
        Game testGame = new Game();

        when(gameRepository.save(any())).thenReturn(testGame);

        Game gameReturned = gameService.create(testGame);

        verify(gameRepository,times(1)).save(any());
        assertNull(gameReturned.getPlayers());
        assertNull(gameReturned.getAchievements());
    }

    @Test
    void addAchievement(){

        Game testGame = new Game(); //Mockando o game necessario
        testGame.setId(GAME_ID);
        Achievement testAchiev = new Achievement(); //Mockando um achiev

        Optional<Game> gameOptional = Optional.of(testGame); //Requerido para o retorno do findById

        Set<Achievement> testSet = new HashSet<>();// mock do set que será passado por parametro
        testSet.add(testAchiev);

        when(gameRepository.findById(anyInt())).thenReturn(gameOptional); //Mock dos comportamentos necessarios.
        when(gameRepository.save(any())).thenReturn(testGame);
        doReturn(testAchiev).when(achievementService).create(any(),any());

        Game returnedGame = gameService.addAchievements(GAME_ID,testSet); // chamada da funcao em teste

        verify(gameRepository,times(1)).findById(any()); //checagens.
        verify(achievementService,times(1)).create(any(),any());
        verify(gameRepository,times(1)).save(any());

        assertEquals(returnedGame.getAchievements(),testSet,"Achievements should match");

    }

    @Test
    void findRegisteredAchievements(){

        Achievement testAchievement = new Achievement();

        when(gameRepository.findAchievementsOfGame(anyInt())).thenReturn(Collections.singleton(testAchievement));

        Set<Achievement> returnedAchievementSet = gameRepository.findAchievementsOfGame(GAME_ID);

        verify(gameRepository,times(1)).findAchievementsOfGame(anyInt());

        assertEquals(Collections.singleton(testAchievement), returnedAchievementSet, "Achievements should match");
    }
}