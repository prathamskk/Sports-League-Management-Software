package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.MainApplication;
import com.project.sportsleaguemanagementproject.model.DatabaseConnector;
import com.project.sportsleaguemanagementproject.singleton.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class TeamManagerScreenController implements Initializable {

    @FXML
    private Circle userIcon;
    @FXML
    private Label accountNameLabel;
    @FXML
    private Label jobLabel;
    @FXML
    private FlowPane flowPane;
    private Connection con;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            con = DatabaseConnector.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        userIcon.setFill(new ImagePattern(ImageLoader.getInstance().loadImage()));
        accountNameLabel.setText(LoginSingleton.getInstance().username);
            accountNameLabel.getStyleClass().add("account-label");
        jobLabel.setText("Team Manager");
            jobLabel.getStyleClass().add("job-label");
        try {
            fillFlowPane();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void fillFlowPane() throws SQLException {
        ResultSet rs = con.createStatement().executeQuery("Select * from player where team_id in (select team_id from team where username='"+LoginSingleton.getInstance().username+"')  ");
        if(rs.next()){
            ResultSet rs1 = con.createStatement().executeQuery("Select * from player where team_id in (select team_id from team where username='"+LoginSingleton.getInstance().username+"')  ");

            while (rs1.next()){
                VBox container = new VBox();
                container.getStyleClass().add("player-flow-pane");
                VBox LabelContainer = new VBox();
                Label playerNameLabel = new Label();
                playerNameLabel.setId("player-label");
                playerNameLabel.setText(rs1.getString("name"));
                Label typeLabel = new Label();
                if(rs1.getString("player_type")==null){
                    typeLabel.setText("");
                }else{
                    typeLabel.setText(rs.getString("player_type"));
                }

                Button viewPlayerStatsButton = new Button("VIEW");
                int playerID = rs1.getInt("player_id");
                viewPlayerStatsButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {

                            PlayerIDSingleton.getInstance().id = playerID;

                        try {
                            SceneSwitcher.switchTo(this.getClass(), e, "TeamViewPlayerStats.fxml","ui/stylesheets/main.css");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });


                container.getChildren().add(LabelContainer);
                LabelContainer.getChildren().add(playerNameLabel);
                LabelContainer.getChildren().add(typeLabel);
                container.getChildren().add(viewPlayerStatsButton);
                flowPane.getChildren().add(container);



                System.out.println(rs1.getString("name"));
            }

        }else{
            flowPane.getChildren().add(new Label("No players in Team"));
        }

    }





    @FXML
    private void logout(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "LoginScreen.fxml","ui/stylesheets/LoginScreenStyleSheet.css");
    }
    @FXML
    private void viewTeamRequestPlayerJoinForm(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "TeamRequestPlayerJoinForm.fxml","ui/stylesheets/main.css");
    }
    @FXML
    private void viewHomeScreen(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "TeamManagerScreen.fxml","ui/stylesheets/main.css");
    }
    @FXML
    private void viewTeamTournamentList(ActionEvent event) throws IOException {

        SceneSwitcher.switchTo(this.getClass(), event, "TeamTournamentList.fxml","ui/stylesheets/main.css");
    }

}
