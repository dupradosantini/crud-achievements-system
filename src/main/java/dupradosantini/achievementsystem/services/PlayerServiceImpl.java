package dupradosantini.achievementsystem.services;

import dupradosantini.achievementsystem.domain.Game;
import dupradosantini.achievementsystem.domain.Player;
import dupradosantini.achievementsystem.repositories.PlayerRepository;
import dupradosantini.achievementsystem.services.exceptions.ObjectNotFoundException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class PlayerServiceImpl implements PlayerService {


    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player findById(Integer id){
        log.debug("Im in the PlayerService, findById Method");
        Optional<Player> obj = playerRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Player not found! id: "+ id + ", Tipo: " + Player.class.getName()));
    }
    @Override
    public Page<Player> findAll(Pageable pageable){
        return playerRepository.findAll(pageable);
    }

    @Override
    public Player update(Integer id, Player obj){
        Player newObj = findById(id);
        newObj.setName(obj.getName());
        newObj.setOwnedGames(obj.getOwnedGames());
        newObj.setEmail(obj.getEmail());
        newObj.setUnlockedAchievements(obj.getUnlockedAchievements());
        newObj.setProfilePic(obj.getProfilePic());
        return playerRepository.save(newObj);
    }

    @Override
    public Player create(Player obj){
        obj.setId(null);//Protegendo caso o requerimento contenha um ID, será gerado posteriormente.
        obj.setUnlockedAchievements(null);
        obj.setOwnedGames(null);
        return playerRepository.save(obj);
    }
    @Override
    public void delete(Integer id){
        findById(id);
        playerRepository.deleteById(id);
    }
    @Override
    public Set<Game> findOwnedGames(Integer id){
        Player thisPlayer = findById(id);
        return thisPlayer.getOwnedGames();
    }

}
