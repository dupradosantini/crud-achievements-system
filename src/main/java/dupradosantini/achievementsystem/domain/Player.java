package dupradosantini.achievementsystem.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

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

    //@UniqueElements
    @NotEmpty(message = "Campo email é obrigatório")
    @Length(max = 30, message = "O email deve ter no máximo 30 caracteres")
    private String email;

    @Length(max=50, message = "O URL para a foto de perfil deve ter no máximo 50 caracteres")
    private String profilePic;

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

    public void setUnlockedAchievements(Set<Achievement> paramAchievements){
        if(paramAchievements == null){
            this.unlockedAchievements = null;
            return;
        }
        Achievement actual;//definindo uma variavel pra lidar com o elemento atual mais pra frente.
        Iterator<Achievement> achievementIterator = paramAchievements.iterator(); //criando iterador no set de achievements
            //Lógica de inserção
            while(achievementIterator.hasNext()){ //enquanto houver elementos no set
                actual = achievementIterator.next(); //atribuindo o elemento atual na variavel actual
                if(ownedGames.contains(actual.getGame())){ //se há o game nos owned-games.
                    //adiciono ao set.
                    if(this.unlockedAchievements == null){//se o conjunto atual é null, precisamos inicializar assim
                        this.unlockedAchievements = new HashSet<>(Arrays.asList(actual));
                    }else { //senao só add
                        this.unlockedAchievements.add(actual);
                    }
                }else{
                    //se o jogador nao possui o jogo necessario retorna erro "jogador nao possui esse jogo"
                    System.out.println("O Jogador não possui o Jogo requerido para desbloquear esse achievement");
                    return;
                }
            }
    }
}
