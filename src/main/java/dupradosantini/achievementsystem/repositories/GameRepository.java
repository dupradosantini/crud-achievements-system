package dupradosantini.achievementsystem.repositories;


import dupradosantini.achievementsystem.domain.Achievement;
import dupradosantini.achievementsystem.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
    @Query("SELECT a FROM Achievement a WHERE a.game.id = :game_id")
    Set<Achievement> findAchievementsOfGame(@Param("game_id") Integer game_id);
}
