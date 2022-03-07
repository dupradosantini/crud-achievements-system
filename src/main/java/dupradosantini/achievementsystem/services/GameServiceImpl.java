package dupradosantini.achievementsystem.services;

import dupradosantini.achievementsystem.domain.Achievement;
import dupradosantini.achievementsystem.domain.Game;
import dupradosantini.achievementsystem.domain.Player;
import dupradosantini.achievementsystem.repositories.GameRepository;
import dupradosantini.achievementsystem.services.exceptions.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public Page<Game> findAll(Pageable pageable){
        return gameRepository.findAll(pageable);
    }

    @Override
    public Set<Achievement> findRegisteredAchievements(Integer id){
        Game thisGame = findById(id);
        return thisGame.getAchievements();
    }
}
