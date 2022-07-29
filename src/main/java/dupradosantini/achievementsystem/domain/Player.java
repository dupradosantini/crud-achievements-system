package dupradosantini.achievementsystem.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.*;



@Table(indexes = @Index(columnList = "email"))
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Player implements Serializable {

    private static final long serialVersionUID = 1L; //serialization ID

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Campo nome é obrigatório")
    @Length(min = 3, max = 30, message = "O nome deve entre 3 e 30 carateres")
    private String name;

    @Column(unique = true)
    @NotEmpty(message = "Campo email é obrigatório")
    @Length(max = 30, message = "O email deve ter no máximo 30 caracteres")
    private String email;

    @Length(max=300, message = "O URL para a foto de perfil deve ter no máximo 50 caracteres")
    private String profilePic;

    @JsonIgnore
    @Length(max=50, message = "Senhas de até 50 caracteres")
    private String password;

    //Relationship with Games
    @ManyToMany
    @JoinTable(name="ownership",
                joinColumns = @JoinColumn(name = "player_id"),
                inverseJoinColumns = @JoinColumn(name="game_id"))
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    private Set<Game> ownedGames;

    @ManyToMany
    @JoinTable(name ="unlocks",
                joinColumns = @JoinColumn(name = "player_id"),
                inverseJoinColumns = @JoinColumn(name="achievement_id"))
    @JsonManagedReference(value = "player-achievement")
    @JsonIgnore
    private Set<Achievement> unlockedAchievements;



    public Player(String name, String email, String profilePic) {
        this.name = name;
        this.email = email;
        this.profilePic = profilePic;
    }
    //Construttor sem foto de perfil.
    public Player(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return getId().equals(player.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", profilePic='" + profilePic + '\'' +
                '}';
    }

     //Used only for bootstrap initialization
    public void setUnlockedAchievements(Set<Achievement> paramAchievements){ //Used only for bootstrap initialization.
        if(paramAchievements == null){
            this.unlockedAchievements = null;
            return;
        }
        if(this.ownedGames == null){
            setOwnedGames(new HashSet<>());
        }
        //criando iterador no set de achievements
        //Lógica de inserção
        for (Achievement actual : paramAchievements) { //enquanto houver elementos no set
            if (this.ownedGames.contains(actual.getGame())) { //se há o game nos owned-games.
                //adiciono ao set.
                if (this.unlockedAchievements == null) {//se o conjunto atual é null, precisamos inicializar assim
                    this.unlockedAchievements = new HashSet<>(Collections.singleton(actual));
                } else { //senao só add
                    this.unlockedAchievements.add(actual);
                }
            } else {
                //se o jogador nao possui o jogo necessario retorna erro "jogador nao possui esse jogo"
                System.out.println("O Jogador não possui o Jogo requerido para desbloquear esse achievement22222");
                System.out.println("O jogo do achievement é:" + actual.getGameId());
                return;
            }
        }
    }

    public void addAchievement(Achievement toBeAddedAchiev){
        if(unlockedAchievements==null){
            unlockedAchievements= new HashSet<>();
        }
        unlockedAchievements.add(toBeAddedAchiev);
    }

    public void addGame(Game toBeAddedGame){
        if (ownedGames==null){
            ownedGames=new HashSet<>();
        }
        if(ownedGames.contains(toBeAddedGame)){
            System.out.println("O jogador já possui esse jogo");
        }else {
            ownedGames.add(toBeAddedGame);
        }
    }
}
