package dupradosantini.achievementsystem.domain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    public static final String TEST_NAME = "TestPlayer";
    public static final String TEST_EMAIL="test.mail@gmail.com";
    public static final String TEST_PROFILE_PIC = "url";

    Player testPlayer;
    Game game1;
    Set<Game> testGame; //using generics
    Achievement achievement1;

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
    @Test
    void getOwnedGames(){
        //given
        game1 = new Game();
        game1.setId(1);
        testGame = new HashSet<>(Collections.singleton(game1));

        //when
        testPlayer.setOwnedGames(testGame);

        //then
        assertEquals(testGame,testPlayer.getOwnedGames(),"Owned Games do not match");
    }
    @Test
    void getUnlockedAchievements(){
        //given
        //Criando o jogo e adicionando a biblioteca do player
        game1 = new Game();
        game1.setId(1);
        testGame = new HashSet<>(Collections.singleton(game1));
        testPlayer.setOwnedGames(testGame);

        //Criando o achievement e adicionando ao set
        achievement1 = new Achievement(game1,"mockAchiev","mockedAchiev","mockedurl");
        achievement1.setId(1);
        Set<Achievement> testAchievements = new HashSet<>(Collections.singleton(achievement1));

        //when
        testPlayer.setUnlockedAchievements(testAchievements);

        //then
        assertEquals(testAchievements,testPlayer.getUnlockedAchievements(),"UnlockedAchievements do not match");
    }

    @Test
    void setUnlockedAchievements(){
        game1 = new Game();
        game1.setId(1);
        testGame = new HashSet<>(Collections.singleton(game1));
        testPlayer.setOwnedGames(testGame);

        //Criando o achievement e adicionando ao set
        achievement1 = new Achievement(game1,"mockAchiev","mockedAchiev","mockedurl");
        achievement1.setId(1);
        Set<Achievement> testAchievements = new HashSet<>(Collections.singleton(achievement1));

        //when
        testPlayer.setUnlockedAchievements(testAchievements);

        assertEquals(testAchievements,testPlayer.getUnlockedAchievements(),"Achievement was not set correctly");

    }
}