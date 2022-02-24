package dupradosantini.achievementsystem.services;

import dupradosantini.achievementsystem.domain.Player;
import dupradosantini.achievementsystem.repositories.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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
}