package dupradosantini.achievementsystem.services;

import dupradosantini.achievementsystem.domain.Achievement;
import dupradosantini.achievementsystem.domain.Game;
import dupradosantini.achievementsystem.repositories.AchievementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AchievementServiceImpl implements AchievementService{

    private final AchievementRepository achievementRepository;

    @Autowired
    public AchievementServiceImpl(AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }

    @Override
    public Achievement findById(Integer id){ //para ser usado talvez num get de achievement.
        Optional<Achievement> obj = achievementRepository.findById(id);
        return obj.orElse(null);
    }

    @Override
    public Achievement create(Achievement obj, Game game){
        obj.setId(null);
        obj.setPlayers(null);
        obj.setGame(game);
        return achievementRepository.save(obj);
    }
}

