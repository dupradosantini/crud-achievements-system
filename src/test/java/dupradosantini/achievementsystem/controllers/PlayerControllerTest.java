package dupradosantini.achievementsystem.controllers;

import dupradosantini.achievementsystem.domain.Achievement;
import dupradosantini.achievementsystem.domain.Game;
import dupradosantini.achievementsystem.domain.Player;
import dupradosantini.achievementsystem.services.GameServiceImpl;
import dupradosantini.achievementsystem.services.PlayerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PlayerControllerTest {

    @Mock
    PlayerServiceImpl playerService;
    @Mock
    GameServiceImpl gameService;

    PlayerController playerController;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        playerController = new PlayerController(playerService,gameService);
        mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();
    }

    @Test
    public void testGetPlayer() throws Exception{
        Player player = new Player();

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
    @Test
    public void testGetOwnedGames() throws Exception{
        Set<Game> gameList = new HashSet<>();
        when(playerService.findOwnedGames(anyInt())).thenReturn(gameList);

        mockMvc.perform(get("/players/1/games"))
                .andExpect(status().isOk());
    }
    @Test
    public void testGetUnlockedAchievements() throws Exception{
        Set<Achievement> achievementSet = new HashSet<>();
        when(playerService.findUnlockedAchievementsByGame(anyInt(),any())).thenReturn(achievementSet);

        mockMvc.perform(get("/players/1/games/1/achievements"))
                .andExpect(status().isOk());
    }
    @Test
    public void testUpdatePlayer() throws Exception{
        Player testPlayer = new Player();

        when(playerService.update(anyInt(),any())).thenReturn(testPlayer);

        int testId = 1;

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/players/"+ testId )
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{" +
                        "}");

        mockMvc.perform(builder).andExpect(status().isOk());
    }

    @Test
    public void testCreatePlayer() throws Exception{
        Player testPlayer = new Player();

        when(playerService.create(any())).thenReturn(testPlayer);

        mockMvc.perform(post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "}"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testDeletePlayer() throws Exception{

        doNothing().when(playerService).delete(anyInt());

        mockMvc.perform(delete("/players/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "}"))
                .andExpect(status().isNoContent());
    }

    @Test
    void addAchievements() throws Exception{

        Player testPlayer = new Player();

        doReturn(testPlayer).when(playerService).unlockAchievements(anyInt(),any());
        int testId = 1;

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/players/"+ testId +"/achievements/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("[{" +
                        "}]");

        mockMvc.perform(builder).andExpect(status().isOk());
    }
}