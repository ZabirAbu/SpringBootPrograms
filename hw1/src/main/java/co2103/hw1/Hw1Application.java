package co2103.hw1;

import co2103.hw1.domain.Player;
import co2103.hw1.domain.Team;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Hw1Application implements CommandLineRunner {

    public static String[] positions = new String[] {"First Team", "Substitute", "On Loan"};
    public static ArrayList<Team> teams = new ArrayList<>();
    public static void main(String[] args) {
        SpringApplication.run(Hw1Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Player player1 = new Player();
        player1.setName("John");
        player1.setDescription("Tall");
        player1.setPosition("First Team");
        player1.setAge(28);

        Player player2 = new Player();
        player2.setName("Lewis");
        player2.setDescription("Fast");
        player2.setPosition("Substitute");
        player2.setAge(30);

        Team team = new Team();


        team.setId(3);
        team.setLeague("Sunday League");
        team.setLocation("Barcelona");

        List<Player> playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);
        team.setPlayers(playerList);
        teams.add(team);
    }
}