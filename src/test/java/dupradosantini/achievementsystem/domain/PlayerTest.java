package dupradosantini.achievementsystem.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    public static final String TEST_NAME = "TestPlayer";
    public static final String TEST_EMAIL="test.mail@gmail.com";
    public static final String TEST_PROFILE_PIC = "url";

    Player testPlayer;

    @BeforeEach
    void setUp() {
        testPlayer = new Player("TestPlayer","test.mail@gmail.com","url");
    }

    @Test
    void getId() {
        Integer idValue = 4;
        testPlayer.setId(idValue);

        assertEquals(idValue,testPlayer.getId(), " PlayerIds do not match.");

    }
    @Test
    void getName(){
        assertEquals(TEST_NAME,testPlayer.getName(),"PlayerNames do not match");
    }
    @Test
    void getEmail(){
        assertEquals(TEST_EMAIL,testPlayer.getEmail(),"Player Emails do not match");
    }
    @Test
    void getProfilePic(){
        assertEquals(TEST_PROFILE_PIC,testPlayer.getProfilePic(),"Player profilePics urls do not match");
    }
}