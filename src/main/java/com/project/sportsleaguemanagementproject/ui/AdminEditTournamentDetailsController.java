package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.singleton.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class AdminEditTournamentDetailsController {
























    @FXML
    public Button logoutButton;


    @FXML
    private void logout(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "LoginScreen.fxml","ui/stylesheets/LoginScreenStyleSheet.css");
    }
    @FXML
    private void viewPendingPlayerTable(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "PendingPlayerList.fxml");
    }
    @FXML
    private void viewTournamentList(ActionEvent event) throws IOException {

        SceneSwitcher.switchTo(this.getClass(), event, "AdminTournamentList.fxml");
    }
    @FXML
    private void viewPlayerVerification(ActionEvent event) throws IOException {

        SceneSwitcher.switchTo(this.getClass(), event, "AdminPlayerVerification.fxml");
    }
}
