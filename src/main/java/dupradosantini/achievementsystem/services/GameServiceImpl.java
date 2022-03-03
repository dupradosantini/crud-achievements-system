package dupradosantini.achievementsystem.services;

import dupradosantini.achievementsystem.domain.Game;
import dupradosantini.achievementsystem.domain.Player;
import dupradosantini.achievementsystem.repositories.GameRepository;
import dupradosantini.achievementsystem.services.exceptions.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class GameServiceImpl implements GameService{

    private final GameRepository gameRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Game findById(Integer id){
        log.debug("Im in the Game Service, findById method");
        Optional<Game> obj = gameRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Game not found! id: "+ id + ", Tipo: " + Game.class.getName()));
    }

    @Override
    public List<Game> findAll(){
        return gameRepository.findAll();
    }
}
