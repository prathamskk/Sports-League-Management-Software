package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.match.Match;
import com.project.sportsleaguemanagementproject.team.Team;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AdminMatchMakerScreenController extends Application {

    @FXML
    TextField hostField;
    @FXML
    TextField team1Field;
    @FXML
    TextField team2Field;
    @FXML
    TextField oversField;
    @FXML
    TextField locationField;
    @FXML
    DatePicker dateOfMatchField;
    @FXML
    TextField startTimeField;
    @FXML
    TextField durationMinutesField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage matchMakerScreen) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MatchMakerScreen.fxml")));
        Scene matchMakerPage = new Scene(root);
        matchMakerScreen.setTitle("Sports League Manager");
        matchMakerScreen.setScene(matchMakerPage);
        matchMakerScreen.setResizable(false);
        matchMakerScreen.show();
    }

    @FXML
    private void onConfirmButton() {
        // TODO: 11/2/2021 check if these two teams exist and if not then throw error;

        Team team1 = new Team();
        Team team2 = new Team();

        Match match = null;
        try {
            match = new Match(
                    hostField.getText(),
                    team1,
                    team2,
                    Integer.parseInt(oversField.getText()),
                    locationField.getText(),
                    dateOfMatchField.getValue(),
                    startTimeField.getText(),
                    Integer.parseInt(durationMinutesField.getText())
            );
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        System.out.println(match);
    }
}
