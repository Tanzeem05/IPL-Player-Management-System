package network.dto;

import data.Club;
import data.Player;
import java.io.Serializable;
import java.util.List;

public class SignUpRespond extends LoginRespond implements Serializable {

    public SignUpRespond(boolean access, Club club, List<Player> marketList,List<Player> Playerlist) {
        super(access, club, marketList,Playerlist);
    }
}
