package data;

import network.dto.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class CentralDatabase {

    private static CentralDatabase instance;

    private CentralDatabase() {
        try {
            System.out.println("Reading files................");
            readFromInputFile();
            readPasswordsFromFile();
            System.out.println("Reading files done.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CentralDatabase getInstance() {
        if (instance == null) {
            instance = new CentralDatabase();
        }
        return instance;
    }

    HashMap<String, Club> clubMap = new HashMap<>();
    List<Player> players = new ArrayList();
    List<Club> clubs = new ArrayList<>();
    List<Player> marketList = new ArrayList<>();
    public static List<Player> AllPlayers=new ArrayList<>();


    private static final String INPUT_FILE_NAME = "IPL-Management-System\\players.txt";
    private static final String OUTPUT_FILE_NAME = "IPL-Management-System\\players.txt";
    private static final String INPUT_PASSWORD_FILE_NAME = "IPL-Management-System\\password.txt";
    private static final String OUTPUT_PASSWORD_FILE_NAME = "IPL-Management-System\\password.txt";

    public void addPlayer(Player p) {
        //p.setId(players.size());
        //p.setId(AllPlayers.size());
        p.setId(players.size());
        players.add(p);
        AllPlayers.add(p);
    }

    public Club addClub(String clubName) {
        if (clubMap.containsKey(clubName)) return clubMap.get(clubName);
        Club club = new Club(clubName);
        clubMap.put(clubName, club);
        club.setId(clubs.size());
        clubs.add(club);
        return club;
    }

    public void readFromInputFile() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        while (true) {
            String line = br.readLine();
            if (line == null) break;
            String[] tokens = line.split(",");

            Player p = new Player();
            Club club = addClub(tokens[4]);
            p.setName(tokens[0]);
            p.setCountry(tokens[1]);
            p.setClub(club);
            p.setClubName(club.getName());
            p.setAge(Integer.parseInt(tokens[2]));
            p.setHeight(Double.parseDouble(tokens[3]));
            p.setPosition(tokens[5]);
            p.setNumber(tokens[6].isEmpty()?-1:Integer.parseInt(tokens[6]));
            p.setSalary(Double.parseDouble(tokens[7]));
            p.setBeingSold(Boolean.parseBoolean(tokens[8]));
            if(tokens.length>9)
            p.setPrice(Double.parseDouble(tokens[9]));
            addPlayer(p);
            if(!p.isBeingSold()) club.addPlayer(p);
            else marketList.add(p);
        }
        br.close();
    }

    public void readPasswordsFromFile() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(INPUT_PASSWORD_FILE_NAME));
        while (true) {
            String line = br.readLine();
            if (line == null) break;
            String[] tokens = line.split(",");
            Club club = addClub(tokens[0]);
            club.setPassword(tokens[1]);
        }
        br.close();
    }

    public void writeToInputFile() throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));
        for(Player p:AllPlayers){
            bw.write(p.getName() + "," + p.getCountry()+","+ p.getAge() +","+ p.getHeight() +","+ p.getClub().getName()+","+ p.getPosition() +","+(p.getNumber()==-1?" ":p.getNumber())+","+p.getSalary()+","+p.isBeingSold()+(p.getPrice()==-1?" ":(","+p.getPrice())));
            bw.write(System.lineSeparator());
        }
        bw.close();
    }

    public void writePasswordToInputFile() throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_PASSWORD_FILE_NAME));
        for (Club club : clubs) bw.write(club.getName() + "," + club.getPassword() + "\n");
        bw.close();
    }

    public Club checkClub(String club) {
        return clubMap.get(club);
    }

    public Club signUpClub(String clubName, String password) {
        for (var club : clubs) {
            if (club.getName().equals(clubName)) return null;
        }
        Club club = new Club(clubName, password);
        clubMap.put(clubName, club);
        club.setId(clubs.size());
        clubs.add(club);
        return club;
    }

    public List<Player> getMarketList() {
        return marketList;
    }

    public synchronized Player sell(SellRequest sellRequest) {
        Player player = AllPlayers.get(sellRequest.getPlayerId());
        if (player.isBeingSold() || player.getClub().getId() != sellRequest.getSellerId()) return null;
        player.setBeingSold(true);
        player.setPrice(sellRequest.getPrice());
        clubs.get(sellRequest.getSellerId()).removePlayer(player);
        marketList.add(player);
        return player;
    }

    public synchronized Player buy(BuyRequest buyRequest) {
        Player player = AllPlayers.get(buyRequest.getPlayerId());
        if (!player.isBeingSold()) return null;
        player.setBeingSold(false);
        player.setClub(clubs.get(buyRequest.getBuyerId()));
        player.setClubName(clubs.get(buyRequest.getBuyerId()).getName());
        player.setPrice(-1);
        clubs.get(buyRequest.getBuyerId()).addPlayer(player);
        LocalDatabase.getInstance().addPlayer(player);
        marketList.remove(player);
        return player;
    }

    public PlayerEditInfo editPlayer(PlayerEditInfo p){
        Player player = players.get(p.getId());
        if (player.isBeingSold() || player.getClub().getId()!= p.getClubId()) return null;
        player.edit(p);
        return new PlayerEditInfo(player);
    }

    public List<Player> getPlayers() {
        return players;
    }


}
