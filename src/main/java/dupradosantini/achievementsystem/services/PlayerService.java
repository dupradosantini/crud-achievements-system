package dupradosantini.achievementsystem.services;

import dupradosantini.achievementsystem.domain.Player;

import java.util.List;

public interface PlayerService {
    Player findById(Integer id);
    List<Player> findAll();
}
