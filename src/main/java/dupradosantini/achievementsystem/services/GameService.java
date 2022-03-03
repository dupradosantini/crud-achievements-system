package dupradosantini.achievementsystem.services;

import dupradosantini.achievementsystem.domain.Game;

import java.util.List;

public interface GameService {
    Game findById(Integer id);
    List<Game> findAll();
}
