package dupradosantini.achievementsystem.repositories;

import dupradosantini.achievementsystem.domain.Achievement;
import dupradosantini.achievementsystem.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    @Query(value= "SELECT a FROM Achievement a INNER JOIN a.players p WHERE p.id = :player_id AND a.game.id= :game_id")
     Set<Achievement> findAchievementsByGame(@Param("player_id") Integer player_id,
                                             @Param("game_id") Integer game_id);
}
