package dupradosantini.achievementsystem.bootstrap;


import dupradosantini.achievementsystem.domain.Achievement;
import dupradosantini.achievementsystem.domain.Game;
import dupradosantini.achievementsystem.domain.Player;
import dupradosantini.achievementsystem.repositories.AchievementRepository;
import dupradosantini.achievementsystem.repositories.GameRepository;
import dupradosantini.achievementsystem.repositories.PlayerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class BootStrapData implements CommandLineRunner {

    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    private final AchievementRepository achievementRepository;

    public BootStrapData(PlayerRepository playerRepository, GameRepository gameRepository, AchievementRepository achievementRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.achievementRepository = achievementRepository;
    }

    @Override
    public void run(String... args){
        Player p1 = new Player("Jogador1Nome","jogador1@gmail.com","urlpraprofilepic");
        Player p2 = new Player("NomeJogador2","jogador2@hotmail.com");
        playerRepository.saveAll(Arrays.asList(p1,p2));

        Game g1 = new Game("Horizon Forbidden West","Adventure");
        Game g2 = new Game("Lost Ark","Action-MMORPG");
        Game g3 = new Game("Enter the Gungeon","Roguelike");
        gameRepository.saveAll(Arrays.asList(g1,g2,g3));

        Achievement gungeon1 = new Achievement(g3,"Gungeon Acolyte","Complete the Tutorial");
        Achievement gungeon2 = new Achievement(g3,"Patron","Spend big at the Acquisitions Department");
        achievementRepository.saveAll(Arrays.asList(gungeon1,gungeon2));

        //Setting which player owns which game.
        Set<Game> p1games = new HashSet<>(Arrays.asList(g3));
        p1.setOwnedGames(p1games);
        //Setting achievements unlocked by player1
        Set<Achievement> playerOneGungeonAchievements = new HashSet<>(Arrays.asList(gungeon1,gungeon2));
        p1.setUnlockedAchievements(playerOneGungeonAchievements);
        playerRepository.saveAll(Arrays.asList(p1));

        System.out.println("Bootstrap Initialization");

        System.out.println("Number of Players: " + playerRepository.count());
        System.out.println("Number of Games: " + gameRepository.count());
        System.out.println("Number of Achievements: " + achievementRepository.count());

        //System.out.println("AchievementId: " + gungeon1.getId());
    }
}
