package dupradosantini.achievementsystem.controllers;

import dupradosantini.achievementsystem.domain.Achievement;
import dupradosantini.achievementsystem.domain.Game;
import dupradosantini.achievementsystem.repositories.GameRepository;
import dupradosantini.achievementsystem.services.GameService;
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
    @GetMapping("/{id}")
    public ResponseEntity<Game> findById(@PathVariable Integer id){
        Game obj = this.gameService.findById(id);
        return ResponseEntity.ok().body(obj);
    }
    @GetMapping
    public ResponseEntity<Page<Game>> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size){
        Pageable paging = PageRequest.of(page,size);
        Page<Game> returnPage = gameService.findAll(paging);
        return ResponseEntity.ok().body(returnPage);
    }
    @GetMapping(value = "{id}/achievements")
    public ResponseEntity<Set<Achievement>> getAllAchievements(@PathVariable Integer id){ //Better then the Players version, change it later.
        Set<Achievement> searchedAchievements = gameService.findRegisteredAchievements(id);
        return ResponseEntity.ok().body(searchedAchievements);
    }
}
