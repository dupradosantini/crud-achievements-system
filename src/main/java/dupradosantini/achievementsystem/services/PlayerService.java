package dupradosantini.achievementsystem.services;

import dupradosantini.achievementsystem.domain.Achievement;
import dupradosantini.achievementsystem.domain.Game;
import dupradosantini.achievementsystem.domain.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface PlayerService {
    Player findById(Integer id);

    Page<Player> findAll(Pageable pageable);

    Player update(Integer id, Player obj);

    Player create(Player obj);

    void delete(Integer id);

    Set<Game> findOwnedGames(Integer id);

    Set<Achievement> findUnlockedAchievementsByGame(Integer playerId, Game searchedGame);

    Player unlockAchievements(Integer playerId, Set<Achievement> achievementSet);

    Player addGame(Integer playerId, Set<Game> gameSet);
}
