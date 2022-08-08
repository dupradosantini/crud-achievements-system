package dupradosantini.achievementsystem.controllers;

import dupradosantini.achievementsystem.domain.Achievement;
import dupradosantini.achievementsystem.domain.Game;
import dupradosantini.achievementsystem.domain.Player;
import dupradosantini.achievementsystem.services.PlayerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import java.util.Set;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/players")
public class PlayerController {

    private final PlayerService playerService;


    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }
    @Operation(summary = "Get a Player by it's ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player found!",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Player.class)) }),
            @ApiResponse(responseCode = "404", description = "Player not found",
            content = @Content)})
    @GetMapping(value="/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    public ResponseEntity<Player> findById( @Parameter(description = "ID of Player to be searched")
                                                @PathVariable Integer id){
        Player obj = this.playerService.findById(id);
        System.out.println("Os jogos do player são:" + obj.getOwnedGames());
        return ResponseEntity.ok().body(obj);
    }
    @Operation(summary = "Gets a page of Players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Page of Players found",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(description = "Page of players"))}),
            @ApiResponse(responseCode = "404", description = "Page not found",
            content = @Content) })
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    public ResponseEntity<Page<Player>> findAll(@Parameter(description = "Page Number")
                                                    @RequestParam(defaultValue = "0") int page,
                                                @Parameter(description = "Amount of Players per page.")
                                                    @RequestParam(defaultValue = "2") int size){
        Pageable paging = PageRequest.of(page,size);
        Page<Player> returnPage = playerService.findAll(paging);
        return ResponseEntity.ok(returnPage);
    }

    @Operation(summary = "Updates an existing player", description = "Used to update players fields" +
            "games or achievements have their own methods.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player updated",
                content = {@Content(mediaType = "application/json",
                schema = @Schema(description = "Updated player", implementation = Player.class))}),
            @ApiResponse(responseCode = "404", description = "Player not found",
                content = @Content) })
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('player:write')")
    public ResponseEntity<Player> update(@PathVariable Integer id,@RequestBody Player obj){
        Player newPlayer = playerService.update(id,obj);
        return ResponseEntity.ok().body(newPlayer);
    }

    @Operation(summary = "Creates a player", description = "When a player is created, its owned games " +
            "and unlocked achievements are non-existent (clean new player), requiring to be added later via PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player created",
                content = {@Content(mediaType = "application/json",
                schema = @Schema(description = "Updated player", implementation = Player.class))}),
            @ApiResponse(responseCode = "404", description = "Player not found",
                content = @Content) })
    @PostMapping
    public ResponseEntity<Player> create(@RequestBody Player obj){
        Player newPlayer = playerService.create(obj);
        //Boa pratica, repassar o endpoint do novo player criado
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newPlayer.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
    @Operation(summary = "Deletes an existing player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player deleted",
                content = @Content),
            @ApiResponse(responseCode = "404", description = "Player not found",
                content = @Content) })
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('player:write')")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        playerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Gets the games owned by a player.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Games retrieved",
                content = @Content),
            @ApiResponse(responseCode = "404", description = "Player not found",
                content = @Content) })
    @GetMapping(value = "/{id}/games")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    public ResponseEntity<Set<Game>> getOwnedGames(@PathVariable Integer id){
        Set<Game> ownedGames = playerService.findOwnedGames(id);
        return ResponseEntity.ok().body(ownedGames);
    }

    @Operation(summary = "Gets the achievements in a specific game that a player has.",
            description = "Fetches the unlocked achievements that a player has in a specific game.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Achievements retrieved",
                content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found - See Error message",
                content = @Content) })
    @GetMapping(value = "/{playerId}/games/{gameId}/achievements")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    public ResponseEntity<Set<Achievement>> getUnlockedAchievements(@PathVariable Integer playerId,
                                                                    @PathVariable Integer gameId){
        Set<Achievement> achievementSet = playerService.findUnlockedAchievementsByGame(playerId,gameId);
        return ResponseEntity.ok().body(achievementSet);
    }

    @Operation(summary = "Adds new achievements to a player", description = "Updates an existing player by adding new Achievements to their unlocked list." +
            "They must have the required game which the achievement belongs to")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Achievements unlocked",
                    content = {@Content(mediaType = "application/json",
                    schema = @Schema(description = "Updated player with newly added achievements", implementation = Player.class))}),
            @ApiResponse(responseCode = "404", description = "Not found - could be game, player or achievement",
                    content = @Content) })
    @PutMapping(value = "/{playerId}/achievements/add")
    @PreAuthorize("hasAuthority('player:write')")
    public ResponseEntity<Player> addAchievements(@PathVariable Integer playerId,
                                                            @RequestBody Set<Achievement> achievementSet){
        Player updatedPlayer = playerService.unlockAchievements(playerId, achievementSet);
        return ResponseEntity.ok().body(updatedPlayer);
    }

    
    @Operation(summary = "Adds an existing game to a player's library", description = "Registers an existing game to a specific player's library," +
            " being able to see it under the 'ownedGames' category.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game has been registered as owned by the player.",
                content = {@Content(mediaType = "application/json",
                schema = @Schema(description = "Updated player with newly added games to their library", implementation = Player.class))}),
            @ApiResponse(responseCode = "404",description = "Not found - could be the game or the player - See the body of response for details",
                content = @Content) })
    @PutMapping(value = "/{playerId}/games/add")
    @PreAuthorize("hasAuthority('player:write')")
    public ResponseEntity<Player> addGame(@PathVariable Integer playerId,
                                          @RequestBody Set<Game> gameSet){
        Player updatedPlayer = playerService.addGame(playerId,gameSet);
        return ResponseEntity.ok().body(updatedPlayer);
    }
}
