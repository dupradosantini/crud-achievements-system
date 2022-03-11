package dupradosantini.achievementsystem.services;

import dupradosantini.achievementsystem.domain.Achievement;
import dupradosantini.achievementsystem.domain.Game;
import dupradosantini.achievementsystem.repositories.AchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.tags.form.AbstractCheckedElementTag;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AchievementServiceImplTest {

    AchievementServiceImpl achievementService;

    @Mock
    AchievementRepository achievementRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(achievementRepository);
        achievementService = new AchievementServiceImpl(achievementRepository);
    }

    @Test
    void findById() {
        Achievement testAchiev = new Achievement();

        Optional<Achievement> achievementOptional = Optional.of(testAchiev);

        when(achievementRepository.findById(anyInt())).thenReturn(achievementOptional);

        Achievement achievReturned = achievementService.findById(anyInt());

        verify(achievementRepository,times(1)).findById(anyInt());

    }

    @Test
    void create() {
        Achievement testAchiev = new Achievement();
        Game testGame = new Game();

        when(achievementRepository.save(any())).thenReturn(testAchiev);

        Achievement achievReturned = achievementService.create(testAchiev,testGame);

        verify(achievementRepository,times(1)).save(any());
        assertNull(achievReturned.getPlayers());
        assertEquals(achievReturned.getGame(),testGame,"Games should match");

    }
}