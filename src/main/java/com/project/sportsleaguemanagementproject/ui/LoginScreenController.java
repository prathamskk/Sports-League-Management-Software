package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.model.DatabaseConnector;
import com.project.sportsleaguemanagementproject.singleton.LoginSingleton;
import com.project.sportsleaguemanagementproject.singleton.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.*;


public class LoginScreenController {
    @FXML
    private Label messageDisplay;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerTeamButton;
    @FXML
    private Button registerPlayerButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private void loadTeamManagerScreen(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "TeamManagerScreen.fxml","ui/stylesheets/TeamManagerScreenController.css");
    }
    @FXML
    private void loadPlayerScreen(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "PlayerScreen.fxml","ui/stylesheets/PlayerScreenController.css");
    }

    @FXML
    private void loadAdminScreen(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "AdminScreen.fxml","ui/stylesheets/AdminScreenController.css");
    }

    @FXML
    private void loadScorekeeperScreen(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "ScoreKeeperScreen.fxml","ui/stylesheets/ScoreKeeperScreenController.css");
    }
    @FXML
    private void loadTeamManagerTeamNameRegistration(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "TeamManagerTeamNameRegistration.fxml","ui/stylesheets/TeamManagerScreenController.css");
    }



    private Connection con;
    @FXML
    private void checkLoginPerformed(ActionEvent e) throws SQLException, IOException {
        con = DatabaseConnector.getConnection();
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (e.getSource() == loginButton){
            if (username.equals("") || password.equals("")) {
                messageDisplay.setText("username or password field is empty");
                } else {
                ResultSet rs = con.createStatement().executeQuery("select * from account where username='" + username + "'and password='" + password + "'");
                if (rs.next()) {
                        String temp = rs.getString("account_type");
                        String admin = "admin";
                        String scorekeeper = "scorekeeper";
                        String team = "team";
                        String player = "player";
                        LoginSingleton.getInstance().username = username;

                        // checks account type
                        if (temp.equals(admin)) {
                            loadAdminScreen(e);
                        }else if (temp.equals(scorekeeper)){
                            loadScorekeeperScreen(e);
                        }else if (temp.equals(team)){
                            if(checkAlreadyRegistration(username)){
                                loadTeamManagerScreen(e);
                            }else{
                                loadTeamManagerTeamNameRegistration(e);
                            }

                        }else if (temp.equals(player)){
                                loadPlayerScreen(e);
                        }
                    } else {
                        System.out.println("WRONG COMBO");
                        messageDisplay.setText("Wrong username and password combination");
                        usernameField.setText("");
                        passwordField.setText("");
                    }
                }
        }
        if (e.getSource() == registerPlayerButton) {
            ResultSet rs = con.createStatement().executeQuery("select * from account where username='" + username + "'");
                if (rs.next()) {
                    System.out.println("USERNAME ALREADY EXISTS");
                    messageDisplay.setText("Username already exists");
                    usernameField.setText("");
                    passwordField.setText("");
                } else if (username.equals("")) {
                    messageDisplay.setText("username field is empty");
                } else if (password.equals("")) {
                    messageDisplay.setText("password field is empty");
                } else {
                    con.createStatement().executeUpdate("INSERT INTO account (username,password,account_type) VALUES ('" + username + "' , '" + password + "','player')");
                    System.out.println("REGISTRATION SUCCESSFUL");
                    messageDisplay.setText("Registration successful");

                }
        }

        if (e.getSource() == registerTeamButton) {
                ResultSet rs = con.createStatement().executeQuery("select * from account where username='" + username + "'");
                if (rs.next()) {
                    System.out.println("USERNAME ALREADY EXISTS");
                    messageDisplay.setText("Username already exists");
                    usernameField.setText("");
                    passwordField.setText("");
                } else if (username.equals("")) {
                    messageDisplay.setText("username field is empty");
                } else if (password.equals("")) {
                    messageDisplay.setText("password field is empty");
                } else {
                    con.createStatement().executeUpdate("INSERT INTO account (username,password,account_type) VALUES ('" + username + "' , '" + password + "','team')");
                    System.out.println("REGISTRATION SUCCESSFUL");
                    messageDisplay.setText("Registration successful");

                }
        }
    }

    private boolean checkAlreadyRegistration(String username) throws SQLException {
        ResultSet rs = con.createStatement().executeQuery("select team_id from team where username='"+username+"'");
        if(rs.next()){
            return true;
        }else return false;
    }

}