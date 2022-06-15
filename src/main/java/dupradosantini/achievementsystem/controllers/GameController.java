package dupradosantini.achievementsystem.controllers;

import dupradosantini.achievementsystem.domain.Achievement;
import dupradosantini.achievementsystem.domain.Game;

import dupradosantini.achievementsystem.domain.Player;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@CrossOrigin("*")
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
    public ResponseEntity<Set<Achievement>> getAllAchievements(@PathVariable Integer id){ //Better than the Players version, change it later.
        Set<Achievement> searchedAchievements = gameService.findRegisteredAchievements(id);
        return ResponseEntity.ok().body(searchedAchievements);
    }

    @Operation(summary = "Creates a new game in our database", description = "Creates a new game in our database, " +
            "which when newly initialized has no owners and no achievements yet, those need to be added later" +
            "through other methods, like PUT @ /games/{id}/achievements/add and PUT @ /players/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game Created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(description = "Newly Created Game", implementation = Game.class))})})
    @PostMapping
    public ResponseEntity<Game> createGame(@RequestBody Game obj){
        Game newGame = gameService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newGame.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "Creates new achievements and add them to a game.", description = "Creates new achievements " +
            "while adding them to the game passed via id, thus expanding the achievement pool of a game.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Achievements Created.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(description = "Game with new achievements.", implementation = Game.class))})})
    @PutMapping(value = "/{id}/achievements/add")
    public ResponseEntity<Game> addAchievement(@PathVariable Integer id, @RequestBody Set<Achievement> achievementSet){
        Game updatedGame = gameService.addAchievements(id,achievementSet);
        return ResponseEntity.ok().body(updatedGame);
    }

    @Operation(summary = "Updates an existing game", description = "Used to update game fields")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(description = "Updated game", implementation = Player.class))}),
            @ApiResponse(responseCode = "404", description = "game not found",
                    content = @Content) })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Game> update(@PathVariable Integer id,@RequestBody Game obj){
        Game newGame = gameService.update(id,obj);
        return ResponseEntity.ok().body(newGame);
    }

    @Operation(summary = "Deletes an existing game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Game not found",
                    content = @Content) })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        gameService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
