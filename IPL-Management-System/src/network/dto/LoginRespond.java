package network.dto;

import data.Club;
import data.Player;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoginRespond implements Serializable {
    boolean access = false;
    Club club = new Club();
    List<Player> marketList = new ArrayList<>();
    List<Player> playerList = new ArrayList<>();

    public LoginRespond(boolean access, Club club, List<Player> marketList, List<Player> playerList) {
        this.access = access;
        this.club = club;
        this.marketList = marketList;
        this.playerList = playerList;
    }

    public boolean isAccess() {
        return access;
    }

    public Club getClub() {
        return club;
    }

    public List<Player> getMarketList() {
        return marketList;
    }

    public List<Player> getPlayerList() { return playerList; }
}
