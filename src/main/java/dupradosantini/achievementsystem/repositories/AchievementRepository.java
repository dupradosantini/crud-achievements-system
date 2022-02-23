package dupradosantini.achievementsystem.repositories;

import dupradosantini.achievementsystem.domain.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Integer> {
}
