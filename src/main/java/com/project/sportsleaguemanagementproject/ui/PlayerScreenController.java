package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.MainApplication;
import com.project.sportsleaguemanagementproject.singleton.ImageLoader;
import com.project.sportsleaguemanagementproject.singleton.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PlayerScreenController implements Initializable {



    @FXML
    private Button logoutButton;

    @FXML
    private ImageView avatarImageView;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        avatarImageView.setImage(ImageLoader.getInstance().loadImage());
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "LoginScreen.fxml","ui/stylesheets/LoginScreenStyleSheet.css");
    }
    @FXML
    private void viewPlayerRegistrationForm(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "PlayerRegistrationForm.fxml","ui/stylesheets/main.css");
    }
    @FXML
    private void viewPlayerTournamentList(ActionEvent event) throws IOException {

        SceneSwitcher.switchTo(this.getClass(), event, "PlayerTournamentList.fxml","ui/stylesheets/main.css");
    }
    @FXML
    private void viewTeamInvites(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "PlayerTeamInvites.fxml","ui/stylesheets/main.css");
    }

}
