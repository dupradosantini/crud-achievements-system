package dupradosantini.achievementsystem.services;

import dupradosantini.achievementsystem.domain.Achievement;
import dupradosantini.achievementsystem.domain.Game;

public interface AchievementService {
    Achievement findById(Integer id);
    Achievement create(Achievement obj, Game game);
}
