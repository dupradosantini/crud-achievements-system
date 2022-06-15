package dupradosantini.achievementsystem.services;

import dupradosantini.achievementsystem.domain.Achievement;
import dupradosantini.achievementsystem.domain.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface GameService {
    Game findById(Integer id);

    Page<Game> findAll(Pageable pageable);

    Set<Achievement> findRegisteredAchievements(Integer id);

    Game create(Game obj);

    Game addAchievements(Integer id, Set<Achievement> achievementSet);

    void delete(Integer id);

    Game update(Integer id, Game obj);
}
