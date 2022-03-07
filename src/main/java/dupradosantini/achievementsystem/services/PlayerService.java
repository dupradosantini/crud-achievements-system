package dupradosantini.achievementsystem.services;

import dupradosantini.achievementsystem.domain.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlayerService {
    Player findById(Integer id);

    Page<Player> findAll(Pageable pageable);

    Player update(Integer id, Player obj);

    Player create(Player obj);

    void delete(Integer id);
}
