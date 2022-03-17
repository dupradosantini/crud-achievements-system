package dupradosantini.achievementsystem.repositories;

import dupradosantini.achievementsystem.domain.Achievement;
import dupradosantini.achievementsystem.domain.Game;
import dupradosantini.achievementsystem.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    /*@Query(value= "SELECT a FROM achievement a INNER JOIN (SELECT * FROM unlocks WHERE unlocks.player_id = :player_id) AS u ON u.achievement_id = a.id AND a.game_id = :game_id",
            nativeQuery = true, name = "Query") //TODO maybe figure out how to make this will work.
    Set<Achievement> findAchievementsByGame(@Param("player_id") Integer player_id,
                                            @Param("game_id") Integer game_id);*/

    @Query(value = "SELECT p.unlockedAchievements FROM Player p WHERE p.id = :player_id")
    Set<Achievement> getAllUnlockedAchievements(@Param("player_id") Integer player_id);

    @Query(value = "SELECT p.ownedGames FROM Player p WHERE p.id = :player_id")
    Set<Game> getAllOwnedGames(@Param("player_id") Integer player_id);
}
