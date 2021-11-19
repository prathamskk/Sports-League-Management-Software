package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.match.Match;
import com.project.sportsleaguemanagementproject.model.DatabaseConnector;
import com.project.sportsleaguemanagementproject.singleton.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminTournamentCreatorController implements Initializable {
    @FXML
    private ChoiceBox<String> noOfTeamsChoiceBox;
    String[] choicesForNoOfTeams = {"2", "4", "8", "16"};
    @FXML
    private Button generateBracketButton;
    @FXML
    private HBox outerHBox;
    @FXML
    private TextArea additionalDetailsTextArea;
    @FXML
    private TextField venueTextField;
    @FXML
    private DatePicker registrationDatePicker;
    @FXML
    private TextField prizePoolTextField;
    @FXML
    private TextField    nameTextField;
    @FXML
    private Label NotifyLabel;

    private Connection con;
    ArrayList<DatePicker> DatePickerArrayList = new ArrayList<>();
    ArrayList<VBox> VBoxArrayList = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        noOfTeamsChoiceBox.getItems().addAll(choicesForNoOfTeams);

        try {
            con = DatabaseConnector.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGenerateBracketButton(){
        outerHBox.getChildren().clear();
        VBoxArrayList.clear();
        DatePickerArrayList.clear();

        int noOfTeams = Integer.parseInt(noOfTeamsChoiceBox.getValue());

        int VBoxIndex = 0;
        int counter = 0;
        int noOfMatches = noOfTeams/2;
        int listOfMatchListIndex = noOfTeams/2;
        for(int i = noOfTeams/2; i > 0; i/=2){
            VBoxArrayList.add(new VBox());
            VBoxArrayList.get(VBoxIndex).setId("VBox" + VBoxIndex);
            VBoxArrayList.get(VBoxIndex).setAlignment(Pos.CENTER);
            VBoxArrayList.get(VBoxIndex).setSpacing(20);
            for(int j = 0; j < noOfMatches; j++){
                DatePicker datePicker = new DatePicker();
                datePicker.setId("DatePicker" + counter);
                DatePickerArrayList.add(datePicker);
                VBoxArrayList.get(VBoxIndex).getChildren().add(createSpacer());
                VBoxArrayList.get(VBoxIndex).getChildren().add(datePicker);
                VBoxArrayList.get(VBoxIndex).getChildren().add(createSpacer());
                counter++;

            }
            --listOfMatchListIndex;
            noOfMatches/=2;
            VBoxIndex++;
        }
        outerHBox.getChildren().addAll(VBoxArrayList);
    }


    @FXML
    private void handleAddTournamentButton() throws SQLException {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO tournament (tournament_name, tournament_prize, registration_date, venue, max_teams,additional_details) VALUES (?,?,?,?,?,?);");
            preparedStatement.setString(1, nameTextField.getText());
            preparedStatement.setString(2, prizePoolTextField.getText());
            preparedStatement.setDate(3, Date.valueOf(registrationDatePicker.getValue()));
            preparedStatement.setString(4, venueTextField.getText());
            preparedStatement.setString(5, noOfTeamsChoiceBox.getValue());
            preparedStatement.setString(6, additionalDetailsTextArea.getText());
            preparedStatement.executeUpdate();
        } catch(Exception SQLException) {
            NotifyLabel.setText("Error: A tournament already exists with same name");//TODO add errors properly this one shows same error for all wrong
        }
        int i;
        for(i=0;i<Integer.parseInt(noOfTeamsChoiceBox.getValue())-1;i++) {
            PreparedStatement preparedStatement1 = con.prepareStatement("INSERT INTO `sportsleaguemanagement`.`match` (`tournament_id`, `match_fixture`, `match_identifier`) VALUES ((select tournament_id from tournament where tournament_name=?), ?, ?);");
            preparedStatement1.setString(1, nameTextField.getText());
            preparedStatement1.setDate(2, Date.valueOf(DatePickerArrayList.get(i).getValue()));
            preparedStatement1.setString(3, venueTextField.getText());
            preparedStatement1.setInt(3, i+1);
            preparedStatement1.executeUpdate();
        }

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


    private Region createSpacer() {
        final Region spacer = new Region();
        // Make it always grow or shrink according to the available space
        VBox.setVgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    @FXML
    private Button logoutButton;


    @FXML
    private void logout(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "LoginScreen.fxml","ui/stylesheets/LoginScreenStyleSheet.css");
    }
    @FXML
    private void viewAdminTournamentCreator(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "AdminTournamentCreator.fxml");
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
