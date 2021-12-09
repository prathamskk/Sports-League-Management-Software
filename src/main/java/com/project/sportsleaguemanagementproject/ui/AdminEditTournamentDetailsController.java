package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.model.DatabaseConnector;
import com.project.sportsleaguemanagementproject.singleton.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminEditTournamentDetailsController implements Initializable {

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

    @FXML
    private HBox outerHBox;
     int MaxTeams;




    private final int id = TournamentTableButtonClickSingleton.getInstance().id;
    private Connection con;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{   userIcon.setFill(new ImagePattern(ImageLoader.getInstance().loadImage()));
            accountNameLabel.setText(LoginSingleton.getInstance().username);
            accountNameLabel.getStyleClass().add("account-label");
            jobLabel.setText("Admin");
            jobLabel.getStyleClass().add("job-label");

            con = DatabaseConnector.getConnection();
            fillData();
            displayEditableBracket();
            fillBracket();
            insertTeamListFlowPane();
        }catch(SQLException ex){
            Logger.getLogger(AdminEditTournamentDetailsController.class.getName()).log(Level.SEVERE, null , ex);
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
      MaxTeams = rs.getInt("max_teams");
    }



    ArrayList<DatePicker> DatePickerArrayList = new ArrayList<>();
    ArrayList<VBox> VBoxArrayList = new ArrayList<>();
    ArrayList<TextField> TextFieldArrayList = new ArrayList<>();
    ArrayList<Button> ButtonArrayList = new ArrayList<>();



    @FXML
    private void displayEditableBracket(){
        outerHBox.getChildren().clear();
        VBoxArrayList.clear();
        DatePickerArrayList.clear();
        TextFieldArrayList.clear();
        ButtonArrayList.clear();

        int noOfTeams =  MaxTeams ;

        int VBoxIndex = 0;
        int counter = 0;
        int counter2 = 0; // textfield counter
        int noOfMatches = noOfTeams/2;
        int listOfMatchListIndex = noOfTeams/2;
        for(int i = noOfTeams/2; i > 0; i/=2){
            VBoxArrayList.add(new VBox());
            VBoxArrayList.get(VBoxIndex).setId("VBox" + VBoxIndex);
            VBoxArrayList.get(VBoxIndex).setAlignment(Pos.CENTER);
            VBoxArrayList.get(VBoxIndex).setSpacing(20);
            for(int j = 0; j < noOfMatches; j++){
                TextField textField1 = new TextField();
                textField1.setId( "TextField" + counter2);
                textField1.setMaxWidth(90);
                textField1.setMaxHeight(25);
                counter2++;
                TextFieldArrayList.add(textField1);


                DatePicker datePicker = new DatePicker();
                datePicker.setId("DatePicker" + counter);
                DatePickerArrayList.add(datePicker);

                TextField textField2 = new TextField();
                textField2.setId( "TextField" + counter2);
                textField2.setMaxWidth(90);
                textField2.setMaxHeight(25);

                Button button = new Button();
                button.setId("Button" + counter);
                button.setText("Stats");
                button.setDisable(true);
                ButtonArrayList.add(button);
                counter2++;
                TextFieldArrayList.add(textField2);

                VBoxArrayList.get(VBoxIndex).getChildren().add(createSpacer());
                VBoxArrayList.get(VBoxIndex).getChildren().add(button);
                VBoxArrayList.get(VBoxIndex).getChildren().add(textField1);
                VBoxArrayList.get(VBoxIndex).getChildren().add(datePicker);
                VBoxArrayList.get(VBoxIndex).getChildren().add(textField2);
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
    private void fillBracket() throws SQLException {
        int i, j,count=0;
        LocalDate todayDate = LocalDate.now();
        for (i = 0; i < MaxTeams - 1; i++) {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM sportsleaguemanagement.match where tournament_id='" + id + "' and match_identifier='" + (i + 1) + "';");
            rs.next();

            DatePickerArrayList.get(i).setValue(LocalDate.parse(rs.getString("match_fixture")));
            ButtonArrayList.get(i).setOnAction((new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {

                    try {
                        MatchIdSingleton.getInstance().id = rs.getInt("match_id");
                        SceneSwitcher.switchTo(this.getClass(), e, "AdminViewMatchStats.fxml","ui/stylesheets/main.css");
                    } catch (IOException | SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }));


            for (j = 0; j < 2; j++) {
                ResultSet rs1 = con.createStatement().executeQuery("SELECT team_name from team where team_id in (SELECT team_id FROM teams_in_match WHERE match_id ='"+rs.getInt("match_id")+"' AND team_index ='"+(j+1)+"');");
                if(rs1.next()) {
                    TextFieldArrayList.get(count).setText(rs1.getString("team_name"));
                }else{
                    TextFieldArrayList.get(count).setText("-N/A-");
                }
                if(rs.getDate("match_fixture").toLocalDate().isBefore(todayDate)) {
                    ButtonArrayList.get(i).setDisable(false);
                    DatePickerArrayList.get(i).setDisable(true);
                    DatePickerArrayList.get(i).setStyle("-fx-opacity: 1");
                    TextFieldArrayList.get(count).setDisable(true);

                }
                count++;
            }

        }
    }
    @FXML
    Label NotifyLabel;
    @FXML
    private void editBracketButton(ActionEvent event) throws SQLException, IOException {
        //checks final registration date is after current date
      LocalDate todayDate = LocalDate.now();


        int i;
        //checks if tournament matches are after final registration date
        if(DatePickerArrayList.get(0).getValue().isBefore(LocalDate.parse(registrationLabel.getText()))){
            NotifyLabel.setText("Match Dates should be after Final Registration Date");
            return;
        }

        //checks if any match date is null
        for(i=0;i<MaxTeams-1;i++) {
            if (DatePickerArrayList.get(i).getValue() == null) {
                NotifyLabel.setText("Enter Date Of all matches");
                return;
            }
        }


        //checks for incorrect match dates (example: finals before semis)
        for(i = MaxTeams-2; i >=1;i--){
            if(DatePickerArrayList.get(i).getValue().isBefore(DatePickerArrayList.get(i-1).getValue())){
                NotifyLabel.setText("Enter Dates in Chronological sequence");
                return;
            }

        }


         int j,count=0;
        for(i=0;i<MaxTeams-1;i++) {
            if(DatePickerArrayList.get(i).getValue()==null){

            }else{
               con.createStatement().executeUpdate("update sportsleaguemanagement.match set match_fixture='"+DatePickerArrayList.get(i).getValue()+"' where  tournament_id='"+id+"' and match_identifier='"+(i+1)+"' ");
            }


            for (j = 0; j < 2; j++) {

                if(TextFieldArrayList.get(count).getText()==null){
                }else{
                    con.createStatement().executeUpdate("update teams_in_match set team_id=(select team_id from team where team_name='"+TextFieldArrayList.get(count).getText()+"') where match_id = (select match_id from sportsleaguemanagement.match where tournament_id='"+id+"'and match_identifier='"+(i+1)+"') AND team_index = '"+(j+1)+"' ;");
                    count++;
                }
            }
            NotifyLabel.setText("Successfully Updated");
        }
    }


    @FXML
    private void handleGiveAccessButton(ActionEvent e) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), e, "AdminTournamentAccess.fxml","ui/stylesheets/main.css");
    }




    private Region createSpacer() {
        final Region spacer = new Region();
        // Make it always grow or shrink according to the available space
        VBox.setVgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    @FXML
    private FlowPane ListOFTeamsFlowPane;

    private void insertTeamListFlowPane() throws SQLException {
        ResultSet rs = con.createStatement().executeQuery("select team_name from team where team_id in(select team_id from teams_in_tournament where tournament_id='" + id + "');");
        while (rs.next()) {
            Label teamLabel = new Label();
            teamLabel.setText(rs.getString("team_name"));
            teamLabel.setOnMouseClicked(new EventHandler() {
                @Override
                public void handle(Event event) {
                    Clipboard clipboard = Clipboard.getSystemClipboard();
                    ClipboardContent content = new ClipboardContent();
                    content.putString(teamLabel.getText());
                    clipboard.setContent(content);
                    NotifyLabel.setText("Copied Name to Clipboard");
                }
            });
            ListOFTeamsFlowPane.getChildren().add(teamLabel);
        }
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
