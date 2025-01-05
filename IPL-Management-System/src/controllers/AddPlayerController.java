package controllers;

import codes.Main;
import data.LocalDatabase;
import data.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import network.client.WriteThreadClient;
import network.dto.AddPlayerInfo;
import java.util.List;
import data.CentralDatabase;

import static javafx.collections.FXCollections.observableArrayList;

public class AddPlayerController{

    private Player player;
    private int playerNumber;
    private double playerSalary;
    private double playerHeight;
    private Main main;
    private int playerAge;
    private List<Player> players = LocalDatabase.getInstance().getPlayers();
    boolean saving=false;
    private HomepageController homepageController;


    @FXML
    private TextField name;

    @FXML
    private TextField country;

    @FXML
    private TextField age;

    @FXML
    private TextField height;

    @FXML
    private TextField number;

    @FXML
    private TextField salary;

    @FXML
    private ComboBox<String> position;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    @FXML
    private Label errorLabel;

    @FXML
    void cancel(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
    }

    @FXML
    void save(ActionEvent event) {
        if(validate() && checkDuplicate()){
//            AddPlayerInfo p = new AddPlayerInfo();
            Player p = new Player();
            p.setName(name.getText());
            p.setCountry(country.getText());
            p.setAge(playerAge);
            p.setSalary(playerSalary);
            p.setHeight(playerHeight);
            p.setNumber(playerNumber);
            System.out.println(CentralDatabase.getInstance().getPlayers().size());
            p.setId(CentralDatabase.getInstance().getPlayers().size());
//            if (position.getSelectionModel().isEmpty())
            p.setPosition(position.getValue());
            p.setClub(LocalDatabase.getInstance().getClub());
            p.setClubName(LocalDatabase.getInstance().getClub().getName());

            LocalDatabase.getInstance().addToList(p);
            //LocalDatabase.getInstance().addPlayer(p);
            CentralDatabase.getInstance().addPlayer(p);
            //LocalDatabase.getInstance().getClub().addPlayer(p);
            CentralDatabase.AllPlayers.add(p);
            for(int i=0;i<CentralDatabase.AllPlayers.size();i++){
                System.out.println(CentralDatabase.AllPlayers.get(i).getName());
            }
            WriteThreadClient.write(p);

            //Main.getHomepageController().updateGUI(CentralDatabase.getInstance().getPlayers());
        }

        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();

    }

    boolean checkDuplicate(){
        for(Player player1 : players){
            if (name.getText().strip().equalsIgnoreCase(player1.getName())) return false;
            if (playerNumber == player1.getNumber()) return false;
        }
        return true;
    }

    boolean validate(){
        //salary
        String salaryString = salary.getText();
        if (salaryString.isEmpty()) return false;
        try{
            playerSalary = Double.parseDouble(salaryString);
        }catch (Exception e){
            errorLabel.setVisible(true);
            return false;
        }
        if (playerSalary<0) {
            errorLabel.setVisible(true);
            return false;
        }

        //height
        String heightString = height.getText();
        if (heightString.isEmpty()) return false;
        try{
            playerHeight = Double.parseDouble(heightString);
        }catch (Exception e){
            errorLabel.setVisible(true);
            return false;
        }
        if (playerHeight<0) {
            errorLabel.setVisible(true);
            return false;
        }

        //height
        String ageString = age.getText();
        if (ageString.isEmpty()) return false;
        try{
            playerAge = Integer.parseInt(ageString);
        }catch (Exception e){
            errorLabel.setVisible(true);
            return false;
        }
        if (playerAge<0) {
            errorLabel.setVisible(true);
            return false;
        }

        String numberString = number.getText();
        if (numberString.isEmpty()) return false;
        try{
            playerNumber = Integer.parseInt(numberString);
        }catch (Exception e){
            errorLabel.setVisible(true);
            return false;
        }
        if (playerNumber<0) {
            errorLabel.setVisible(true);
            return false;
        }

        return true;
    }
    @FXML
    public void initialize() {
        position.setItems(observableArrayList("WicketKeeper", "Bowler", "Batsman", "Allrounder"));
        position.setValue("Batsman"); // Pre-select "Forward" as the default value
    }

    public void init(){
//        this.main = main;
//        main.showAddPlayerPage(new ActionEvent());
    }

    public void init(HomepageController homepageController, LocalDatabase localDatabase) {
        //homepageController.init();
    }
}
