package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.model.DatabaseConnector;
import com.project.sportsleaguemanagementproject.singleton.ButtonClickSingleton;
import com.project.sportsleaguemanagementproject.singleton.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerVerifyScreenController implements Initializable {
    @FXML
    private Label usernameLabel;

    private final String id = ButtonClickSingleton.getInstance().id;
    private Connection con;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            con = DatabaseConnector.getConnection();
            fillData();
        }catch(SQLException ex){
            Logger.getLogger(TournamentListController.class.getName()).log(Level.SEVERE, null , ex);
        }
    }
    private void fillData() throws SQLException {
        ResultSet rs = con.createStatement().executeQuery("select * from player where username='"+id+"';");
        rs.next();
        usernameLabel.setText(rs.getString("username"));

    }

    @FXML
    private Button logoutButton;


    @FXML
    private void logout(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "LoginScreen.fxml","ui/stylesheets/LoginScreenStyleSheet.css");
    }
    @FXML
    private void verifyregisterPlayer(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "VerifyPlayerRegistration.fxml");
    }
    @FXML
    private void viewTournamentList(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "TournamentList.fxml");
    }
    @FXML
    private void viewPlayerVerification(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "PlayerVerification.fxml");
    }
}
