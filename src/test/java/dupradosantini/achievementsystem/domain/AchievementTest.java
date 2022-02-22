package dupradosantini.achievementsystem.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AchievementTest {
    public static final String TEST_NAME = "TestAchievement";
    public static final String TEST_DESCRIPTION="Test Description of the Achievement";
    public static final String TEST_PICTURE = "url";
    public static final Integer TEST_GAME_ID = 1;

    Achievement testAchievement;
    //Achievement testAchievement2;
    Game game1;
    //Game game2;

    @BeforeEach
    void setUp() {
        game1 = new Game();
        game1.setId(1);
        testAchievement = new Achievement(game1,"TestAchievement","Test Description of the Achievement","url");
        testAchievement.setId(2);
    }

    @Test
    void testEquals() {
        Achievement compareObject = new Achievement(game1,"TestAchievement","Test Description of the Achievement");
        compareObject.setId(2);
        assertTrue(testAchievement.equals(compareObject),"Achievements are not equal");
    }

    @Test
    void getId() {
        Integer idValue = 4;
        testAchievement.setId(idValue);

        assertEquals(idValue,testAchievement.getId(), " AchievementIds do not match.");
    }

    @Test
    void getGameId() {
        assertEquals(TEST_GAME_ID,testAchievement.getOwnedByGameId(),"Achievement gameIDs does not match");
    }

    @Test
    void getName() {
        assertEquals(TEST_NAME,testAchievement.getName(),"AchievementNames do not match");
    }

    @Test
    void getDescription() {
        assertEquals(TEST_DESCRIPTION,testAchievement.getDescription(),"Achievements description do not match");
    }

    @Test
    void getPicture() {
        assertEquals(TEST_PICTURE,testAchievement.getPicture(),"Achievement pictures do not match");
    }
}