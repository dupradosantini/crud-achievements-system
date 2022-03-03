package dupradosantini.achievementsystem.services;

import dupradosantini.achievementsystem.domain.Player;

import java.util.List;

public interface PlayerService {
    Player findById(Integer id);

    List<Player> findAll();

    Player update(Integer id, Player obj);

    Player create(Player obj);
}
