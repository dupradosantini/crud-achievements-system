package dupradosantini.achievementsystem.services;

import dupradosantini.achievementsystem.domain.Achievement;
import dupradosantini.achievementsystem.domain.Game;
import dupradosantini.achievementsystem.domain.Player;
import dupradosantini.achievementsystem.repositories.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
//@RunWith(JUnitPlatform.class) //Not needed in JUnit 5
class PlayerServiceImplTest {

    private final Integer PLAYER_ID = 1;

    PlayerServiceImpl playerService;

    @Mock
    PlayerRepository playerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(playerRepository);
        playerService = new PlayerServiceImpl(playerRepository);
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
        //verify(playerRepository,never().findAll()); for when we have a find all method
    }
    @Test
    void findAllPlayers(){
        Player player = new Player();
        player.setId(PLAYER_ID);
        List<Player> list = Arrays.asList(player);

        when(playerRepository.findAll()).thenReturn(list);

        List<Player> listReturned = playerService.findAll();
        assertNotNull(listReturned, "Null list returned (expected something)");
        assertFalse(listReturned.isEmpty(),"List shouldn't be empty");
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

        testPlayer.setOwnedGames(new HashSet<>(Arrays.asList(testGame)));
        testPlayer.setUnlockedAchievements(new HashSet<>(Arrays.asList(testAchievement)));
        //when
        when(playerService.create(testPlayer)).thenReturn(testPlayer);
        Player createdPlayer = playerService.create(testPlayer);

        //then
        assertNull(createdPlayer.getUnlockedAchievements(),"Unlocked Achievements should be null");
        assertNull(createdPlayer.getOwnedGames(),"Owned games should be null");
        assertEquals(createdPlayer.getEmail(),testPlayer.getEmail(),"Emails should match");
        assertEquals(createdPlayer.getName(),testPlayer.getName(),"Names should match");
        assertEquals(createdPlayer.getId(),testPlayer.getId(),"Ids should match");
        assertEquals(createdPlayer.getProfilePic(),testPlayer.getProfilePic(),"Profile pics should match");
    }
}