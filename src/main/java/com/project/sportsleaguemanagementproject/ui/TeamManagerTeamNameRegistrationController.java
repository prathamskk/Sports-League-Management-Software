package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.model.DatabaseConnector;
import com.project.sportsleaguemanagementproject.singleton.LoginSingleton;
import com.project.sportsleaguemanagementproject.singleton.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TeamManagerTeamNameRegistrationController implements Initializable {
    private Connection con;
    private final String username = LoginSingleton.getInstance().username;
    @FXML
    private TextField teamNameTextField;
    @FXML
    private Label notifyLabel;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            con = DatabaseConnector.getConnection();




            } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @FXML
    private void handleRegisterNameButton(ActionEvent event) throws SQLException, IOException {
        notifyLabel.setText("");
       ResultSet rs =  con.createStatement().executeQuery("select team_id from team where team_name = '"+teamNameTextField.getText()+"';");
       if(rs.next()){
           notifyLabel.setText("This name is already taken");
       }else{
           con.createStatement().executeUpdate("insert into team (team_name,username) values ('"+teamNameTextField.getText()+"','"+username+"'); ");
           SceneSwitcher.switchTo(this.getClass(), event, "TeamManagerScreen.fxml","ui/stylesheets/TeamManagerScreenController.css");

       }

    }


}
