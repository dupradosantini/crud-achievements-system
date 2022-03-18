package dupradosantini.achievementsystem.controllers;

import dupradosantini.achievementsystem.domain.Achievement;
import dupradosantini.achievementsystem.domain.Game;
import dupradosantini.achievementsystem.services.GameServiceImpl;
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

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


import static org.mockito.ArgumentMatchers.anyInt;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GameControllerTest {

    @Mock
    GameServiceImpl gameService;

    GameController gameController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gameController = new GameController(gameService);
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
    }

    @Test
    void gameFindById() throws Exception{
        Game game = new Game();
        game.setId(1);

        when(gameService.findById(anyInt())).thenReturn(game);

        mockMvc.perform(get("/games/1"))
                .andExpect(status().isOk());
    }

    @Test
    void gameFindAll() throws Exception{

        Page<Game> gamePage = Page.empty();
        Pageable paging = PageRequest.of(0,2);

        when(gameService.findAll(paging)).thenReturn(gamePage);

        mockMvc.perform(get("/games"))
                .andExpect(status().isOk());

    }
    @Test
    void getRegisteredAchievements() throws Exception{
        Set<Achievement> testAchievements = new HashSet<>();

        when(gameService.findRegisteredAchievements(anyInt())).thenReturn(testAchievements);

        mockMvc.perform(get("/games/1/achievements"))
                .andExpect(status().isOk());
    }
    @Test
    void gameCreate() throws Exception{
        Game testGame = new Game();
        testGame.setId(1);

        when(gameService.create(any())).thenReturn(testGame);



        mockMvc.perform(post("/games")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "            \"id\": 3,\n" +
                        "            \"name\": \"JogoTestePost\",\n" +
                        "            \"coverImage\": null,\n" +
                        "            \"genre\": \"TestePost\",\n" +
                        "            \"achievements\": []\n" +
                        "}"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void gameAddAchievement() throws Exception{
        Game testGame = new Game();

        when(gameService.addAchievements(anyInt(),any())).thenReturn(testGame);
        int testId = 1;

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/games/"+ testId + "/achievements/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                .content("[]");

        mockMvc.perform(builder).andExpect(status().isOk());
    }
}