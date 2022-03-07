package dupradosantini.achievementsystem.services;

import dupradosantini.achievementsystem.domain.Achievement;
import dupradosantini.achievementsystem.domain.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface GameService {
    Game findById(Integer id);
    Page<Game> findAll(Pageable pageable);
    Set<Achievement> findRegisteredAchievements(Integer id);
}
