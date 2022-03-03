package dupradosantini.achievementsystem.controllers;

import dupradosantini.achievementsystem.domain.Game;
import dupradosantini.achievementsystem.repositories.GameRepository;
import dupradosantini.achievementsystem.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<List<Game>> findAll(){
        List<Game> list = gameService.findAll();
        return ResponseEntity.ok().body(list);
    }
}
