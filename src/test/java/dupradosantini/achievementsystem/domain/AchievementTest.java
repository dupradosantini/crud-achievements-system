package dupradosantini.achievementsystem.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AchievementTest {
    public static final String TEST_NAME = "TestAchievement";
    public static final String TEST_DESCRIPTION="Test Description of the Achievement";
    public static final String TEST_PICTURE = "url";
    public static final Integer TEST_GAME_ID = 5;

    Achievement testAchievement;

    @BeforeEach
    void setUp() {
        testAchievement = new Achievement(5,"TestAchievement","Test Description of the Achievement","url");
        testAchievement.setId(2);
    }

    @Test
    void testEquals() {
        Achievement compareObject = new Achievement(5,"TestAchievement","Test Description of the Achievement");
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
        assertEquals(TEST_GAME_ID,testAchievement.getGameId(),"Achievement gameIDs does not match");
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