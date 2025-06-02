package co2103.hw1.domain;
import java.util.ArrayList;
import java.util.List;

public class Team {
    private int id;
    private String location;
    private String league;
    private List<Player> players = new ArrayList<Player>();

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getLeague() {
        return league;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
