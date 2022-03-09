package dupradosantini.achievementsystem.controllers;

import dupradosantini.achievementsystem.domain.Achievement;
import dupradosantini.achievementsystem.domain.Game;
import dupradosantini.achievementsystem.domain.Player;
import dupradosantini.achievementsystem.repositories.GameRepository;
import dupradosantini.achievementsystem.services.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @Operation(summary = "Gets a game by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game retrieved",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(description = "Game Schema", implementation = Game.class))}),
            @ApiResponse(responseCode = "404", description = "Game not found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<Game> findById(@PathVariable Integer id){
        Game obj = this.gameService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @Operation(summary = "Gets a page of games.",description = "Returns a page of games based on the " +
            "input parameter, the page number and the number of games per page.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game page retrieved",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content) })
    @GetMapping
    public ResponseEntity<Page<Game>> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size){
        Pageable paging = PageRequest.of(page,size);
        Page<Game> returnPage = gameService.findAll(paging);
        return ResponseEntity.ok().body(returnPage);
    }

    @Operation(summary = "Gets the available achievements of a specific game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Achievements retrieved",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Game not found",
                    content = @Content) })
    @GetMapping(value = "{id}/achievements")
    public ResponseEntity<Set<Achievement>> getAllAchievements(@PathVariable Integer id){ //Better then the Players version, change it later.
        Set<Achievement> searchedAchievements = gameService.findRegisteredAchievements(id);
        return ResponseEntity.ok().body(searchedAchievements);
    }
}
