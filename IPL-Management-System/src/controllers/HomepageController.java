package controllers;

import codes.HomepageUpdater;
import codes.Main;
import data.Club;
import data.LocalDatabase;
import data.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.StageStyle;
import network.client.WriteThreadClient;
import network.dto.LogoutRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomepageController {


    public Label addPlayerError;
    public Button searchIcon1;
    public Button cancelSearch1;
    public TextField Country;
    public TextField Club;
    List<Player> players = new ArrayList<>();
    private int searchOption = 0;
    private String searchString;
    private Main main;
    HomepageUpdater homepageUpdater;
    Club club;
    int list = 0; //1 -> club players, 2 -> market players 3-> addplayer
    AddPlayerController addPlayerController;

    @FXML
    private Button homeButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button marketButton;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;

    @FXML
    private Button nameSearch;

    @FXML
    private Button positionSearch;

    @FXML
    private Button countrySearch;

    @FXML
    private Button countryOrCLubSearch;

    @FXML
    private Button salarySearch;

    @FXML
    private Button maxAge;

    @FXML
    private Button maxHeight;

    @FXML
    private Button maxSalary;

    @FXML
    private Button countryWiseButton;

    @FXML
    private Button totalSalaryButton;

    @FXML
    private Label clubNameLabel;

    @FXML
    private TextField textField;

    @FXML
    private Button searchIcon;

    @FXML
    private Label messageLabel;

    @FXML
    private Button cancelSearch;

    @FXML
    private Label notFoundLabel;

    @FXML
    private TextField fromSalary;

    @FXML
    private TextField toSalary;

    @FXML
    private Label middleLable;

    @FXML
    private Label dollarLabel;

    @FXML
    private Label fromLabel;

    @FXML
    private Label cntrytxt;

    @FXML
    private Label clubtxt;


    void showFromTo(boolean show) {
        if (show) showTextBox(false);
        fromLabel.setVisible(show);
        middleLable.setVisible(show);
        dollarLabel.setVisible(show);
        toSalary.setVisible(show);
        fromSalary.setVisible(show);
        toSalary.clear();
        fromSalary.clear();
        searchIcon.setVisible(show);
        cancelSearch.setVisible(show);
        if (show) fromSalary.requestFocus();
        if (show) cancelSearch.setLayoutY(96);
        else cancelSearch.setLayoutY(55);
    }

    void showTextBox(boolean show) {
        if (show) {
            showFromTo(false);
            showCountryClub(false);
        }
        textField.setVisible(show);
        textField.clear();
        searchIcon.setVisible(show);
        cancelSearch.setVisible(show);
        if (show) textField.requestFocus();
        if (show) cancelSearch.setLayoutY(96);
        else cancelSearch.setLayoutY(55);
    }

    void showCountryClub(boolean show) {
        if (show)
        {
            showFromTo(false);
            showTextBox(false);
        }
        cntrytxt.setVisible(show);
        clubtxt.setVisible(show);
        Country.setVisible(show);
        Club.setVisible(show);
        Country.clear();
        Club.clear();
        searchIcon1.setVisible(show);
        cancelSearch1.setVisible(show);
        if (show) {
            Country.requestFocus();
            Club.requestFocus();
        }
//        if (show) cancelSearch1.setLayoutY(96);
//        else cancelSearch1.setLayoutY(55);
    }

    void search() {
        homepageUpdater.updateGUI(searchOption, searchString);
    }

    @FXML
    void searchPressed(ActionEvent event) {
        if (searchOption == 4) searchString = fromSalary.getText() + "," + toSalary.getText();
        else if(searchOption == 10) searchString = Country.getText() + "," + Club.getText();
        else searchString = textField.getText();
        search();
    }

    @FXML
    void nameSearchPressed(ActionEvent event) {
        showTextBox(true);
        messageLabel.setVisible(false);
        textField.setPromptText("Type player name");
        searchOption = 1;
    }

    @FXML
    void positionSearchPressed(ActionEvent event) {
        showTextBox(true);
        messageLabel.setVisible(false);
        textField.setPromptText("Type player postion");
        searchOption = 2;
    }

    @FXML
    void countrySearchPressed(ActionEvent event) {
        showTextBox(true);
        messageLabel.setVisible(false);
        textField.setPromptText("Type the country name");
        searchOption = 3;
    }

    @FXML
    void countryOrClubSearchPressed(ActionEvent event) {
        showCountryClub(true);
        messageLabel.setVisible(false);
        Country.setPromptText("Type the country name");
        Club.setPromptText("Type the club name or ANY");
        searchOption = 10;
    }

    @FXML
    void salarySearchPressed(ActionEvent event) {
        messageLabel.setVisible(false);
        showFromTo(true);
        searchOption = 4;
    }

    @FXML
    void maxAgePressed(ActionEvent event) {
        showFromTo(false);
        showTextBox(false);
        //cancelSearch.setVisible(true);
        messageLabel.setVisible(false);
        searchOption = 5;
        search();
    }

    @FXML
    void maxHeightPressed(ActionEvent event) {
        showFromTo(false);
        showTextBox(false);
        //cancelSearch.setVisible(true);
        messageLabel.setVisible(false);
        searchOption = 6;
        search();
    }

    @FXML
    void maxSalaryPressed(ActionEvent event) {
        showFromTo(false);
        showTextBox(false);
        //cancelSearch.setVisible(true);
        messageLabel.setVisible(false);
        searchOption = 7;
        search();
    }

    @FXML
    void countryWisePressed(ActionEvent event) {
        showFromTo(false);
        showTextBox(false);
        cancelSearch.setVisible(true);
        messageLabel.setVisible(false);
        searchOption = 8;
        search();
    }

    @FXML
    void totalSalaryPressed(ActionEvent event) {
        if (list != 1) return;
        searchOption = 9;
        search();
    }

    @FXML
    void cancelSearchPressed(ActionEvent event) {
        showFromTo(false);
        showTextBox(false);
        cancelSearch.setVisible(false);

        messageLabel.setVisible(false);
        searchOption = 0;
        search();
    }

    @FXML
    void cancelSearchPressed1(ActionEvent event) {
        showFromTo(false);
        showTextBox(false);
        showCountryClub(false);
        cancelSearch.setVisible(false);
        cancelSearch1.setVisible(false);

        messageLabel.setVisible(false);
        searchOption = 0;
        search();
    }

    @FXML
    void showHome(ActionEvent event) throws Exception {
        showCountryClub(false);
        showFromTo(false);
        showTextBox(false);


        //if (list == 1) return;
        list = 1;




        totalSalaryButton.setVisible(true);
        maxAge.setVisible(true);
        maxHeight.setVisible(true);
        maxSalary.setVisible(true);
        clubNameLabel.setText(club.getName());

        homepageUpdater.updateGUI(list);


    }

    @FXML
    void showMarket(ActionEvent event) {
        if (list == 2) return;
        list = 2;
        totalSalaryButton.setVisible(false);
        maxAge.setVisible(false);
        maxHeight.setVisible(false);
        maxSalary.setVisible(false);
        clubNameLabel.setText("Marketplace");
        homepageUpdater.updateGUI(list);
    }

    @FXML
    void addplayer(ActionEvent event) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/fxml/AddPlayerForm.fxml"));
        try {
            DialogPane dialogPane = fxmlLoader.load();
            Dialog dialog = new Dialog();
            dialog.setDialogPane(dialogPane);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.showAndWait();
        } catch (IOException o) {
            o.printStackTrace();
        }
        main.showHomePage();
    }

    @FXML
    void logOut(ActionEvent event) {
        WriteThreadClient.write(new LogoutRequest());
        try {
            main.showLoginPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addPlayerError() {
        addPlayerError.setVisible(true);
    }


    public void init(Main main) throws Exception {
        this.main = main;
        club = LocalDatabase.getInstance().getClub();
        homepageUpdater = new HomepageUpdater(this);
        LocalDatabase.getInstance().setHomepageController(this);
        showHome(new ActionEvent());
    }


    public Label getNotFoundLabel() {
        return notFoundLabel;
    }

    public GridPane getGrid() {
        return grid;
    }

    synchronized public void setMessageLabel(String message) {
        if (message == null) {
            messageLabel.setVisible(false);
            return;
        }
        messageLabel.setText(message);
        messageLabel.setVisible(true);
    }

    public HomepageUpdater getHomepageUpdater() {
        return homepageUpdater;
    }


}


