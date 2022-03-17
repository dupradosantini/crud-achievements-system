package dupradosantini.achievementsystem.services;

import dupradosantini.achievementsystem.domain.Achievement;
import dupradosantini.achievementsystem.domain.Game;
import dupradosantini.achievementsystem.domain.Player;
import dupradosantini.achievementsystem.repositories.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
//@RunWith(JUnitPlatform.class) //Not needed in JUnit 5
class PlayerServiceImplTest {

    private final Integer PLAYER_ID = 1;

    @Mock
    PlayerServiceImpl playerService;

    @Mock
    PlayerRepository playerRepository;

    @Mock
    AchievementServiceImpl achievementService;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(playerService);
        MockitoAnnotations.openMocks(playerRepository);
        MockitoAnnotations.openMocks(achievementService);
        playerService = new PlayerServiceImpl(playerRepository,achievementService);
    }

    @Test
    void findPlayerById() {
        Player player = new Player();
        player.setId(PLAYER_ID);

        Optional<Player> playerOptional = Optional.of(player);

        when(playerRepository.findById(anyInt())).thenReturn(playerOptional);

        Player playerReturned =  playerService.findById(PLAYER_ID);

        assertNotNull(playerReturned, "Null player returned (expected something)");
        verify(playerRepository,times(1)).findById(anyInt());
    }
    @Test
    void findAllPlayers(){

        Page<Player> playerPage = Page.empty();
        Pageable paging = PageRequest.of(0,3);

        when(playerRepository.findAll(paging)).thenReturn(playerPage);

        Page<Player> pageReturned = playerService.findAll(paging);
        assertNotNull(pageReturned, "Null page returned (expected something)");
        assertTrue(pageReturned.isEmpty(),"Page should be empty");
        verify(playerRepository,times(1)).findAll(paging);
    }
    @Test
    void updatePlayer(){
        Player player = new Player();
        player.setId(PLAYER_ID);

        playerRepository.save(player);

        verify(playerRepository,times(1)).save(player);
    }
    @Test
    void createPlayer(){
        //given
        Player testPlayer = new Player("test","testmail","testurl");
        testPlayer.setId(PLAYER_ID);

        Game testGame = new Game();
        testGame.setId(1);

        Achievement testAchievement = new Achievement();
        testAchievement.setId(1);
        testAchievement.setGame(testGame);

        testPlayer.setOwnedGames(new HashSet<>(Collections.singleton(testGame)));
        testPlayer.setUnlockedAchievements(new HashSet<>(Collections.singleton(testAchievement)));
        //when
        when(playerService.create(testPlayer)).thenReturn(testPlayer);
        Player createdPlayer = playerService.create(testPlayer);

        //then
        verify(playerRepository,times(1)).save(any());
        assertNull(createdPlayer.getUnlockedAchievements(),"Unlocked Achievements should be null");
        assertNull(createdPlayer.getOwnedGames(),"Owned games should be null");
        assertEquals(createdPlayer.getEmail(),testPlayer.getEmail(),"Emails should match");
        assertEquals(createdPlayer.getName(),testPlayer.getName(),"Names should match");
        assertEquals(createdPlayer.getId(),testPlayer.getId(),"Ids should match");
        assertEquals(createdPlayer.getProfilePic(),testPlayer.getProfilePic(),"Profile pics should match");
    }
    @Test
    void deletePlayer(){

        Player player = new Player();
        player.setId(1);

        Mockito.when(playerRepository.findById(anyInt())).thenReturn(Optional.of(player));
        playerService.delete(PLAYER_ID);

        verify(playerRepository,times(1)).deleteById(anyInt());
    }
    @Test
    void findOwnedGames(){
        Player player = new Player();
        player.setId(1);

        Optional<Player> playerOptional = Optional.of(player);

        doReturn(playerOptional).when(playerRepository).findById(anyInt());

        Set<Game> returnedSet = playerService.findOwnedGames(player.getId());

        assertNull(returnedSet,"returned set should be null");
    }

    @Test
    void findUnlockedAchievementsHappyFlow(){
        Game testGame = new Game();
        testGame.setId(1);
        Achievement testAchievement = new Achievement(testGame,"test","test");
        Set<Achievement> achievementSet = new HashSet<>();
        achievementSet.add(testAchievement);

        Player player = new Player();
        player.setId(1);


        testGame.setAchievements(achievementSet);
        Set<Game> testSet = new HashSet<>();
        testSet.add(testGame);

        player.setOwnedGames(testSet);
        player.setUnlockedAchievements(achievementSet);


        doReturn(achievementSet).when(playerRepository).getAllUnlockedAchievements(anyInt());
        doReturn(testSet).when(playerRepository).getAllOwnedGames(anyInt());

        Set<Achievement> returnedSet = playerService.findUnlockedAchievementsByGame(player.getId(),testGame);

        assertEquals(achievementSet,returnedSet,"Sets should be equal");
    }
    //TODO Not Happy flow test above

    @Test
    void unlockAchievements(){
        Player testPlayer = new Player();
        testPlayer.setId(PLAYER_ID);

        Game testGame = new Game();
        testGame.setId(PLAYER_ID);
        testPlayer.setOwnedGames(Collections.singleton(testGame));

        Achievement testAchievement = new Achievement();
        testAchievement.setId(PLAYER_ID);
        testAchievement.setGame(testGame);
        testGame.setAchievements(Collections.singleton(testAchievement));

        Optional<Player> playerOptional = Optional.of(testPlayer);

        doReturn(playerOptional).when(playerRepository).findById(anyInt());
        doReturn(testAchievement).when(achievementService).findById(anyInt());
        when(playerRepository.save(any())).thenReturn(testPlayer);

       Player returnedPlayer = playerService.unlockAchievements(testPlayer.getId(),Collections.singleton(testAchievement));

       verify(playerRepository,times(1)).save(any());
       assertEquals(Collections.singleton(testAchievement),returnedPlayer.getUnlockedAchievements(),"Achievements should match");
    }

    @Test
    void addGame(){
        Player testPlayer = new Player();
        testPlayer.setId(PLAYER_ID);

        Game testGame = new Game();
        testGame.setId(PLAYER_ID);

        Optional<Player> playerOptional = Optional.of(testPlayer);

        doReturn(playerOptional).when(playerRepository).findById(anyInt());
        when(playerRepository.save(any())).thenReturn(testPlayer);

        Player returnedPlayer = playerService.addGame(testPlayer.getId(),Collections.singleton(testGame));

        verify(playerRepository,times(1)).save(any());
        assertEquals(Collections.singleton(testGame),returnedPlayer.getOwnedGames(),"Games should match");


    }
}