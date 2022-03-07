package dupradosantini.achievementsystem.controllers;

import dupradosantini.achievementsystem.domain.Player;
import dupradosantini.achievementsystem.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/players")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
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
}
