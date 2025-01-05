package controllers;

import data.CentralDatabase;
import data.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;

import java.io.IOException;

public class PlayerBox {

    private Player player;
    private boolean isClubPlayer; // Flag to indicate if the player belongs to clubPlayers
    private double price = 5;

    @FXML
    private Label nameRow;

    @FXML
    private Label clubRow;

    @FXML
    private Button sellButton;

    @FXML
    private Button detailsButton;

    @FXML
    void details(ActionEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/fxml/playerDialogue.fxml"));
        try {
            DialogPane dialogPane = fxmlLoader.load();
            PlayerDialogueController playerDialogueController = fxmlLoader.getController();
            playerDialogueController.init(player, true);
            Dialog dialog = new Dialog();
            dialog.setDialogPane(dialogPane);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void sell(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/fxml/confirmSell.fxml"));
        try {
            DialogPane dialogPane = fxmlLoader.load();
            ConfirmSell controller = fxmlLoader.getController();
            controller.init(player);
            Dialog dialog = new Dialog();
            dialog.setDialogPane(dialogPane);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Player getPlayer() {
        return player;
    }

    // Modified method to include the isClubPlayer flag
    public void inti(Player player, boolean isClubPlayer) {
        this.player = player;
        this.isClubPlayer = isClubPlayer;
        System.out.println("Initialized PlayerBox for player: " + player.getName() + ", isClubPlayer: " + isClubPlayer);
        updatePlayerInfoUI();
    }

    // Update UI elements with player data
    public void updatePlayerInfoUI() {
        nameRow.setText(player.getName());
        clubRow.setText(player.getCountry() + ", " + player.getPosition() + ", " + player.getNumber());

        // Show or hide the Sell button based on the isClubPlayer flag
        sellButton.setVisible(isClubPlayer);
    }
}
