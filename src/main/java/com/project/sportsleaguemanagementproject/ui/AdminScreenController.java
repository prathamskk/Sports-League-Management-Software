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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminScreenController implements Initializable {

    @FXML
    public Button logoutButton;
    @FXML
    public BorderPane borderPane;
    @FXML
    private Parent verifyPlayerRegistration;
    @FXML
    private Parent tournamentList;
    @FXML
    private Parent playerVerification;

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
    private void verifyregisterPlayer(){
        borderPane.setCenter(verifyPlayerRegistration);
    }
    @FXML
    private void viewTournamentList(){
        borderPane.setCenter(tournamentList);
    }
    @FXML
    private void viewPlayerVerification() {
        borderPane.setCenter(playerVerification);
    }


    private Parent loadScreen(String sc) throws IOException{
        return FXMLLoader.load(Objects.requireNonNull(getClass().getResource(sc)));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            playerVerification = loadScreen("PlayerVerification.fxml");
            verifyPlayerRegistration = loadScreen("VerifyPlayerRegistration.fxml");
            tournamentList = loadScreen("TournamentList.fxml");
        } catch (IOException ex){
            ex.printStackTrace();
        }

    }


}
