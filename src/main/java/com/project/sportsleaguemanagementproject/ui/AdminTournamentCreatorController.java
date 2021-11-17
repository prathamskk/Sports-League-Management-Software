package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.match.Match;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminTournamentCreatorController implements Initializable {
    @FXML
    private ChoiceBox<String> noOfTeamsChoiceBox;
    String[] choicesForNoOfTeams = {"2", "4", "8", "16", "32", "64"};
    @FXML
    private Button generateBracketButton;
    @FXML
    private HBox outerHBox;

    @FXML
    private void handleGenerateBracketButton(){
        outerHBox.getChildren().clear();
        int noOfTeams = Integer.parseInt(noOfTeamsChoiceBox.getValue());

        ArrayList<ArrayList<Match>> listOfMatchList = new ArrayList<>();
        ArrayList<VBox> VBoxArrayList = new ArrayList<>();
        int VBoxIndex = 0;
        int counter = 0;
        int noOfMatches = noOfTeams/2;
        int listOfMatchListIndex = noOfTeams/2;
        for(int i = noOfTeams/2; i > 0; i/=2){
            VBoxArrayList.add(new VBox());
            VBoxArrayList.get(VBoxIndex).setId("VBox" + VBoxIndex);
            VBoxArrayList.get(VBoxIndex).setAlignment(Pos.CENTER);
            VBoxArrayList.get(VBoxIndex).setSpacing(20 * (VBoxIndex + 1) * 1);
            listOfMatchList.add(new ArrayList<Match>());
            for(int j = 0; j < noOfMatches; j++){
                listOfMatchList.get(noOfTeams/2 - listOfMatchListIndex).add(new Match());
                DatePicker datePicker = new DatePicker();
                datePicker.setId("DatePicker" + counter);
                VBoxArrayList.get(VBoxIndex).getChildren().add(datePicker);
                counter++;
            }
            VBoxIndex++;
            listOfMatchListIndex--;
            noOfMatches/=2;
        }
        outerHBox.getChildren().addAll(VBoxArrayList);
    }
    public ArrayList<ArrayList<Match>> thing(int noOfTeams){
        ArrayList<ArrayList<Match>> listOfMatchList = new ArrayList<>();
        int noOfMatches = noOfTeams/2;
        int listOfMatchListIndex = noOfTeams/2;
        for(int i = noOfTeams/2; i > 0; i/=2){
            listOfMatchList.add(new ArrayList<Match>());
            for(int j = 0; j < noOfMatches; j++){
                listOfMatchList.get(noOfTeams/2 - listOfMatchListIndex).add(new Match());
            }
            listOfMatchListIndex--;
            noOfMatches/=2;
        }
        return listOfMatchList;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        noOfTeamsChoiceBox.getItems().addAll(choicesForNoOfTeams);
    }
}
