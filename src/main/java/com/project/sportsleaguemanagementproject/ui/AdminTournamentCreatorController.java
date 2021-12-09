package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.match.Match;
import com.project.sportsleaguemanagementproject.model.AdminTournamentAccessTable;
import com.project.sportsleaguemanagementproject.model.DatabaseConnector;
import com.project.sportsleaguemanagementproject.model.ModelTournamentList;
import com.project.sportsleaguemanagementproject.singleton.ImageLoader;
import com.project.sportsleaguemanagementproject.singleton.LoginSingleton;
import com.project.sportsleaguemanagementproject.singleton.SceneSwitcher;
import com.project.sportsleaguemanagementproject.singleton.TournamentTableButtonClickSingleton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;

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
    @FXML
    private Button addTournamentButton;


    private Connection con;
    ArrayList<DatePicker> DatePickerArrayList = new ArrayList<>();
    ArrayList<VBox> VBoxArrayList = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userIcon.setFill(new ImagePattern(ImageLoader.getInstance().loadImage()));
        accountNameLabel.setText(LoginSingleton.getInstance().username);
            accountNameLabel.getStyleClass().add("account-label");
        jobLabel.setText("Admin");
            jobLabel.getStyleClass().add("job-label");
        noOfTeamsChoiceBox.getItems().addAll(choicesForNoOfTeams);

        try {
            con = DatabaseConnector.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGenerateBracketButton(){
        if(noOfTeamsChoiceBox.getValue()==null){
            NotifyLabel.setText("select max no of teams");
            return;
        }

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
        addTournamentButton.setVisible(true);
    }


    @FXML
    private void handleAddTournamentButton(ActionEvent event) throws SQLException, IOException {


        //checks final registration date is after current date
        LocalDate todayDate = LocalDate.now();
        if(registrationDatePicker.getValue().isBefore(todayDate)){
            NotifyLabel.setText("Final Registration Date has already passed");
            return;
        }

        int i;
        //checks if tournament matches are after final registration date
        if(DatePickerArrayList.get(0).getValue().isBefore(registrationDatePicker.getValue())){
            NotifyLabel.setText("Match Dates should be after Final Registration Date");
            return;
        }

        //checks if any match date is null
        for(i=0;i<Integer.parseInt(noOfTeamsChoiceBox.getValue())-1;i++) {
            if (DatePickerArrayList.get(i).getValue() == null) {
                NotifyLabel.setText("Enter Date Of all matches");
                return;
            }
        }

        //checks for incorrect match dates (example: finals before semis)
        for(i = Integer.parseInt(noOfTeamsChoiceBox.getValue())-2; i >=1;i--){
            if(DatePickerArrayList.get(i).getValue().isBefore(DatePickerArrayList.get(i-1).getValue())){
                NotifyLabel.setText("Enter Dates in Chronological sequence");
                return;
            }

        }
        try {
            if(nameTextField.getText().equals("")){
                NotifyLabel.setText("Error: Tournament Name cannot be empty");
                return;
            }
            if(registrationDatePicker.getValue()==null){
                NotifyLabel.setText("Error: Registration Date cannot be empty");
                return;
            }

            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO tournament (tournament_name, tournament_prize, registration_date, venue, max_teams,additional_details) VALUES (?,?,?,?,?,?);");
            preparedStatement.setString(1, nameTextField.getText());
            preparedStatement.setString(2, prizePoolTextField.getText());
            preparedStatement.setDate(3, java.sql.Date.valueOf(registrationDatePicker.getValue()));
            preparedStatement.setString(4, venueTextField.getText());
            preparedStatement.setString(5, noOfTeamsChoiceBox.getValue());
            preparedStatement.setString(6, additionalDetailsTextArea.getText());
            preparedStatement.executeUpdate();
        } catch(Exception SQLException) {
            NotifyLabel.setText("Error: Check Data entered");
        }

        for(i=0;i<Integer.parseInt(noOfTeamsChoiceBox.getValue())-1;i++) {
            PreparedStatement preparedStatement1 = con.prepareStatement("INSERT INTO `sportsleaguemanagement`.`match` (`tournament_id`, `match_fixture`, `match_identifier`) VALUES ((select tournament_id from tournament where tournament_name=?), ?, ?);");
            preparedStatement1.setString(1, nameTextField.getText());
            preparedStatement1.setDate(2, java.sql.Date.valueOf(DatePickerArrayList.get(i).getValue()));
            preparedStatement1.setInt(3, i+1);
            preparedStatement1.executeUpdate();
        }

        ResultSet rs = con.createStatement().executeQuery("select tournament_id from tournament where tournament_name = '"+nameTextField.getText()+"'");
        rs.next();
        int j,count=0;
        for(i=0;i<Integer.parseInt(noOfTeamsChoiceBox.getValue())-1;i++) {
            for (j = 0; j < 2; j++) {

                con.createStatement().executeUpdate("INSERT INTO teams_in_match VALUES ( '0' ,(select match_id from sportsleaguemanagement.match  where tournament_id='"+rs.getInt("tournament_id")+"'and match_identifier='"+(i+1)+"') ,'"+(j+1)+"');");
                }
            }
        TournamentTableButtonClickSingleton.getInstance().id = rs.getInt("tournament_id");
        SceneSwitcher.switchTo(this.getClass(), event, "AdminTournamentAccess.fxml","ui/stylesheets/main.css");

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
    private Circle userIcon;
    @FXML
    private Label accountNameLabel;
    @FXML
    private Label jobLabel;



    @FXML
    private void logout(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "LoginScreen.fxml","ui/stylesheets/LoginScreenStyleSheet.css");
    }
    @FXML
    private void viewAdminTournamentCreator(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "AdminTournamentCreator.fxml","ui/stylesheets/main.css");
    } @FXML
    private void viewHomeScreen(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "AdminScreen.fxml","ui/stylesheets/main.css");
    }
    @FXML
    private void viewPendingPlayerTable(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "AdminPendingPlayerList.fxml","ui/stylesheets/main.css");
    }
    @FXML
    private void viewTournamentList(ActionEvent event) throws IOException {

        SceneSwitcher.switchTo(this.getClass(), event, "AdminTournamentList.fxml","ui/stylesheets/main.css");
    }
    @FXML
    private void viewPlayerVerification(ActionEvent event) throws IOException {

        SceneSwitcher.switchTo(this.getClass(), event, "AdminPlayerVerification.fxml","ui/stylesheets/main.css");
    }
}
