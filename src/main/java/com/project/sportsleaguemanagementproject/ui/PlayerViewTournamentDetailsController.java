package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.model.DatabaseConnector;
import com.project.sportsleaguemanagementproject.singleton.SceneSwitcher;
import com.project.sportsleaguemanagementproject.singleton.TournamentTableButtonClickSingleton;
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

public class PlayerViewTournamentDetailsController implements Initializable {
    @FXML
    private Label nameLabel;
    @FXML
    private Label prizeLabel;
    @FXML
    private Label registrationLabel;
    @FXML
    private Label venueLabel;
    @FXML
    private Label maxTeamsLabel;
    @FXML
    private Label additionalDetailsLabel;






    private final int id = TournamentTableButtonClickSingleton.getInstance().id;
    private Connection con;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            con = DatabaseConnector.getConnection();
            fillData();
           // TODO displayBrackets();
        }catch(SQLException ex){
            Logger.getLogger(PlayerViewTournamentDetailsController.class.getName()).log(Level.SEVERE, null , ex);
        }
    }

    private void fillData() throws SQLException {
        int noOfTeamsRegistered=0;
        ResultSet resultSet = con.createStatement().executeQuery("select team_id from teams_in_tournament where tournament_id='"+id+"';");
        while (resultSet.next()){
            noOfTeamsRegistered++;

        }
        ResultSet rs = con.createStatement().executeQuery("select * from tournament where tournament_id='"+id+"';");
        rs.next();
        nameLabel.setText(rs.getString  ("tournament_name"));
        prizeLabel.setText(rs.getString  ("tournament_prize"));
                registrationLabel.setText(rs.getString  ("registration_date"));
                venueLabel.setText(rs.getString  ("venue"));
                maxTeamsLabel.setText(noOfTeamsRegistered+"/"+rs.getInt("max_teams"));
                additionalDetailsLabel.setText(rs.getString  ("additional_details"));
    }

    @FXML
    private Button logoutButton;


    @FXML
    private void logout(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "LoginScreen.fxml","ui/stylesheets/LoginScreenStyleSheet.css");
    }
    @FXML
    private void viewPlayerRegistrationForm(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "PlayerRegistrationForm.fxml");
    }
    @FXML
    private void viewPlayerTournamentList(ActionEvent event) throws IOException {

        SceneSwitcher.switchTo(this.getClass(), event, "PlayerTournamentList.fxml");
    }
    @FXML
    private void viewTeamInvites(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "PlayerTeamInvites.fxml");
    }

}
