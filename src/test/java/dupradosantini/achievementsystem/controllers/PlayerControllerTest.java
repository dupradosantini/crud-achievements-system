package dupradosantini.achievementsystem.controllers;

import dupradosantini.achievementsystem.domain.Player;
import dupradosantini.achievementsystem.services.PlayerService;
import dupradosantini.achievementsystem.services.PlayerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PlayerControllerTest {

    private final Integer PLAYER_ID = 1;

    @Mock
    PlayerServiceImpl playerService;

    PlayerController playerController;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        playerController = new PlayerController(playerService);
        mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();
    }

    @Test
    public void testGetPlayer() throws Exception{
        Player player = new Player();
        player.setId(PLAYER_ID);

        when(playerService.findById(anyInt())).thenReturn(player);

        mockMvc.perform(get("/players/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllPlayers() throws Exception{

        Page<Player> playerPage = Page.empty(); // creating empty page
        Pageable paging = PageRequest.of(0,3); //creating pageable object for paging request

        when(playerService.findAll(paging)).thenReturn(playerPage);

        mockMvc.perform(get("/players"))
                .andExpect(status().isOk());
    }
}