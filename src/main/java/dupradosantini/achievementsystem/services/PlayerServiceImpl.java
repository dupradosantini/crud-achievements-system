package dupradosantini.achievementsystem.services;

import dupradosantini.achievementsystem.domain.Player;
import dupradosantini.achievementsystem.repositories.PlayerRepository;
import dupradosantini.achievementsystem.services.exceptions.ObjectNotFoundException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public List<Player> findAll(){
        return playerRepository.findAll();
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

}
