package dupradosantini.achievementsystem.services;

import dupradosantini.achievementsystem.domain.Achievement;
import dupradosantini.achievementsystem.domain.Game;
import dupradosantini.achievementsystem.domain.Player;

import dupradosantini.achievementsystem.repositories.PlayerRepository;
import dupradosantini.achievementsystem.services.exceptions.ObjectNotFoundException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Override
    public Set<Achievement> findUnlockedAchievementsByGame(Integer playerId, Game searchedGame){
        Player thisPlayer = findById(playerId);
        Set<Achievement> allAchievements = thisPlayer.getUnlockedAchievements();
        Set<Achievement> returnSet = new HashSet<>();

        Achievement actual;
        Iterator<Achievement> achievementIterator = allAchievements.iterator();

        Set<Game> ownedGames = thisPlayer.getOwnedGames();

        if(ownedGames.contains(searchedGame)) {
            while (achievementIterator.hasNext()) { //enquanto houver achievements no set
                actual = achievementIterator.next();
                if (actual.getGameId().equals(searchedGame.getId())) {//se o gameID do achiev for igual ao gameId passado
                    returnSet.add(actual);       // adiciono ao set
                }
            }
            if (returnSet.isEmpty()){
                System.out.println("O jogador não possui conquistas nesse jogo");
                //Possivel exceção customizada.
            }
        }else{
            System.out.println("O jogado não possui o jogo especificado.");
            throw new RuntimeException("O jogador não possui o jogo especificado.");
            //Possivel exceção customizada.
        }
        return returnSet;
    }
}
