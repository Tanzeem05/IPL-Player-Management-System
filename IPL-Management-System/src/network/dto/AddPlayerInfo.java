package network.dto;

import data.Player;

import java.io.Serializable;
import java.util.List;

public class AddPlayerInfo implements Serializable {
    List<Player> players;
    public List<Player> getPlayers() {return players;}
    public void setPlayers(List<Player> players) {this.players = players;}
}
