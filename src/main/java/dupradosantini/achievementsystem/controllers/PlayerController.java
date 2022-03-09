package dupradosantini.achievementsystem.controllers;

import dupradosantini.achievementsystem.domain.Achievement;
import dupradosantini.achievementsystem.domain.Game;
import dupradosantini.achievementsystem.domain.Player;
import dupradosantini.achievementsystem.services.GameService;
import dupradosantini.achievementsystem.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import java.util.Set;

@RestController
@RequestMapping(value = "/players")
public class PlayerController {

    private final PlayerService playerService;

    private final GameService gameService;

    @Autowired
    public PlayerController(PlayerService playerService, GameService gameService) {
        this.playerService = playerService;
        this.gameService = gameService;
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Player> findById(@PathVariable Integer id){
        Player obj = this.playerService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping
    public ResponseEntity<Page<Player>> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size){
        Pageable paging = PageRequest.of(page,size);
        Page<Player> returnPage = playerService.findAll(paging);
        return ResponseEntity.ok(returnPage);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<Player> update(@PathVariable Integer id,@RequestBody Player obj){
        Player newPlayer = playerService.update(id,obj);
        return ResponseEntity.ok().body(newPlayer);
    }

    @PostMapping
    public ResponseEntity<Player> create(@RequestBody Player obj){
        Player newPlayer = playerService.create(obj);
        //Boa pratica, repassar o endpoint do novo player criado
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newPlayer.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        playerService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping(value = "/{id}/games")
    public ResponseEntity<Set<Game>> getOwnedGames(@PathVariable Integer id){
        Set<Game> ownedGames = playerService.findOwnedGames(id);
        return ResponseEntity.ok().body(ownedGames);
    }
    @GetMapping(value = "/{playerId}/games/{gameId}/achievements")
    public ResponseEntity<Set<Achievement>> getUnlockedAchievements(@PathVariable Integer playerId, @PathVariable Integer gameId){
        Game searchedGame = gameService.findById(gameId);
        Set<Achievement> achievementSet = playerService.findUnlockedAchievementsByGame(playerId,searchedGame);
        return ResponseEntity.ok().body(achievementSet);
    }
}
