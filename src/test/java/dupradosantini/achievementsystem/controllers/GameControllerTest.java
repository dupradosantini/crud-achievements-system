package dupradosantini.achievementsystem.controllers;

import dupradosantini.achievementsystem.domain.Game;
import dupradosantini.achievementsystem.services.GameServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
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
}