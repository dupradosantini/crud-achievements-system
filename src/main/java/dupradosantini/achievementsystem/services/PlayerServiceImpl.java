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

    private final AchievementServiceImpl achievementService;



    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, AchievementServiceImpl achievementService) {
        this.playerRepository = playerRepository;
        this.achievementService = achievementService;
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
        //Getting the required player
        //Player thisPlayer = findById(playerId); could be used to check if player exists

        //Allocating all the achievements
        Set<Achievement> allAchievements = playerRepository.getAllUnlockedAchievements(playerId);
        if(allAchievements == null){
            allAchievements = new HashSet<>();
        }
        //This will be our return set
        Set<Achievement> returnSet = new HashSet<>();


        //Allocating the players owned games.
        Set<Game> ownedGames = playerRepository.getAllOwnedGames(playerId);
        if(ownedGames == null){
            ownedGames = new HashSet<>();
        }

        if(ownedGames.contains(searchedGame)) {
            for (Achievement actual : allAchievements) { //enquanto houver achievements no set
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

    @Override
    public Player unlockAchievements(Integer playerId, Set<Achievement> achievementSet){

        Player updatedPlayer = findById(playerId);
        Set<Game> ownedGames = updatedPlayer.getOwnedGames();

        for (Achievement actual : achievementSet) {
            actual = achievementService.findById(actual.getId());
            if (ownedGames.contains(actual.getGame())) {
                updatedPlayer.addAchievement(actual);
            } else {
                //se o jogador nao possui o jogo necessario retorna erro "jogador nao possui esse jogo"
                System.out.println("O Jogador não possui o Jogo requerido para desbloquear esse achievement"
                        + "o jogo é: " + actual.getGameId());
            }
        }
        //Returns the saved player.
        return playerRepository.save(updatedPlayer);
    }

    @Override
    public Player addGame(Integer playerId, Set<Game> gameSet){

        Player updatedPlayer = findById(playerId);

        for(Game actual : gameSet){
            updatedPlayer.addGame(actual);
        }

        return playerRepository.save(updatedPlayer);
    }
}
