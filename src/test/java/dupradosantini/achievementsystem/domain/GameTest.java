package dupradosantini.achievementsystem.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    public static final String TEST_NAME = "TestGame";
    public static final String TEST_GENRE="testGenre";
    public static final String TEST_COVER_IMAGE = "url";

    Game testGame;
    Player player1;
    Set<Player> players;

    @BeforeEach
    void setUp() {
        testGame = new Game("TestGame","url","testGenre");
    }

    @Test
    void getId() {
        Integer idValue = 4;
        testGame.setId(idValue);

        assertEquals(idValue,testGame.getId(), "GameIds do not match.");
    }

    @Test
    void getName() {
        assertEquals(TEST_NAME,testGame.getName(),"Game Names do not match");
    }

    @Test
    void getCoverImage() {
        assertEquals(TEST_COVER_IMAGE,testGame.getCoverImage(),"Games CoverImage Urls do not match");
    }

    @Test
    void getGenre() {
        assertEquals(TEST_GENRE,testGame.getGenre(),"Games genre do not match");
    }

    @Test
    void getPlayers(){
        //given
        player1 = new Player(); //creating the test player
        player1.setId(1);  //setting its id (avoid null pointer)
        players = new HashSet<>(Arrays.asList(player1)); //(creating the player set)

        //when
        testGame.setPlayers(players); //assigning the player set to the games list of players

        //then
        assertEquals(players,testGame.getPlayers(),"Players do not match");
    }
}