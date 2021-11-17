package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.match.Match;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
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
            for(int j = 0; j < noOfMatches; j++){
                DatePicker datePicker = new DatePicker();
                datePicker.setId("DatePicker" + counter);
                VBoxArrayList.get(VBoxIndex).getChildren().add(createSpacer());
                VBoxArrayList.get(VBoxIndex).getChildren().add(datePicker);
                VBoxArrayList.get(VBoxIndex).getChildren().add(createSpacer());
                counter++;
            }
            VBoxIndex++;
        }
        outerHBox.getChildren().addAll(VBoxArrayList);
    }
//    public void thing(int x){
//        ArrayList<ArrayList<Integer>> clist = new ArrayList<>();
//        int c = 0;
//        int d = x/2;
//        int e = x/2;
//        for(int i = x/2; i > 0; i/=2){
//            clist.add(new ArrayList<Integer>());
//            for(int j = 0; j < d; j++){
//                clist.get(x/2 - e).add(c);
//                c++;
//            }
//            e--;
//            d/=2;
//        }
//        System.out.println(clist);
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println("c: " + c);
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        noOfTeamsChoiceBox.getItems().addAll(choicesForNoOfTeams);
    }
    private Region createSpacer() {
        final Region spacer = new Region();
        // Make it always grow or shrink according to the available space
        VBox.setVgrow(spacer, Priority.ALWAYS);
        return spacer;
    }
}
