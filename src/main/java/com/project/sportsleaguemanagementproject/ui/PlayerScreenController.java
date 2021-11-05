package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PlayerScreenController implements Initializable{
    @FXML
    public Button logoutButton;
    @FXML
    public BorderPane borderPane;
    @FXML
    private Parent playerRegistration;
    @FXML
    private Parent tournamentList;

    @FXML
    private void logout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginScreen.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource("ui/stylesheets/LoginScreenStyleSheet.css")).toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void registerPlayer(){
        borderPane.setCenter(playerRegistration);
    }
    @FXML
    private void viewTournamentList(){
        borderPane.setCenter(tournamentList);
    }

    private Parent loadScreen(String sc) throws IOException{
        return FXMLLoader.load(Objects.requireNonNull(getClass().getResource(sc)));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            playerRegistration = loadScreen("PlayerRegistrationForm.fxml");
            tournamentList = loadScreen("TournamentList.fxml");
        } catch (IOException ex){
            ex.printStackTrace();
        }

    }
}
