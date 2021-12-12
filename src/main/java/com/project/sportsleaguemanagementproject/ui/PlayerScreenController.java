package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.MainApplication;
import com.project.sportsleaguemanagementproject.model.DatabaseConnector;
import com.project.sportsleaguemanagementproject.singleton.ImageLoader;
import com.project.sportsleaguemanagementproject.singleton.LoginSingleton;
import com.project.sportsleaguemanagementproject.singleton.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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

public class PlayerScreenController implements Initializable {




    @FXML
    private Circle userIcon;
    @FXML
    private Label accountNameLabel;
    @FXML
    private Label jobLabel;
    private Connection con;
    @FXML
    private VBox mainVbox;
    private String verification_status;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            con = DatabaseConnector.getConnection();
            displayInfo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        userIcon.setFill(new ImagePattern(ImageLoader.getInstance().loadImage()));
        accountNameLabel.setText(LoginSingleton.getInstance().username);
        accountNameLabel.getStyleClass().add("account-label");
        jobLabel.setText("Player");
        jobLabel.getStyleClass().add("job-label");

    }
    private boolean checkAlreadyRegistered() throws SQLException {
        ResultSet rs = con.createStatement().executeQuery("select verification_status from player where username = '"+LoginSingleton.getInstance().username+"'");
        if(rs.next()){
            verification_status = rs.getString("verification_status");
            return true;

        }else{
            return false;
        }
    }
    private void displayInfo() throws SQLException {
        if(checkAlreadyRegistered()){
            if(verification_status.equals("verified")){
                //FOR VERIFIED PLAYERS
                ResultSet rs = con.createStatement().executeQuery("select player_id from player where username='"+LoginSingleton.getInstance().username+"'");
                rs.next();
                String playerID = rs.getString("player_id");
                Label informationLabel = new Label("Welcome! "+LoginSingleton.getInstance().username+" ");
                Label StatsLabel = new Label("Lifetime Stats");
                StatsLabel  .setStyle("-fx-font-size:16");
                informationLabel.setStyle("-fx-text-fill:black");
                informationLabel.setStyle("-fx-font-size:32");
                mainVbox.getChildren().add(informationLabel);

                mainVbox.setAlignment(Pos.TOP_LEFT);
                mainVbox.setSpacing(50);
                ResultSet resultSet = con.createStatement().executeQuery("select * from player where player_id='"+playerID+"'");
                resultSet.next();
                 if(resultSet.getString("player_type")==null || resultSet.getString("player_type").equals("all_rounder") || resultSet.getString("player_type").equals("batsman")){
                    //batsman null and all round players
                     GridPane gridPane = new GridPane();
                    gridPane.setVgap(15);
                    gridPane.setHgap(15);
                    Label BallsLabel     = new Label("Balls Batted")     ;
                    Label RunsLabel      = new Label("Total Runs")       ;
                    Label SixsLabel      = new Label("6s")               ;
                    Label FoursLabel     = new Label("4s")               ;
                    Label StrikeRateLabel     = new Label("Strike Rate") ;

                    Label BallsLabelValue    = new Label("Balls Batted")  ;
                    Label RunsLabelValue    = new Label("Total Runs")    ;
                    Label SixsLabelValue    = new Label("6s")            ;
                    Label FoursLabelValue   = new Label("4s")            ;
                    Label StrikeRateLabelValue = new Label("Strike Rate");
                    ResultSet rs1 = con.createStatement().executeQuery("SELECT SUM(stat_balls) as result from sportsleaguemanagement.statistics where player_id='"+playerID+"' and type='batting';");
                    ResultSet rs2 = con.createStatement().executeQuery("SELECT SUM(stat_runs) as  result from sportsleaguemanagement.statistics where player_id='"+playerID+"' and type='batting';");
                    ResultSet rs3 = con.createStatement().executeQuery("SELECT SUM(stat_6s) as  result from sportsleaguemanagement.statistics where player_id='"+playerID+"' and type='batting';");
                    ResultSet rs4 = con.createStatement().executeQuery("SELECT SUM(stat_4s) as  result from sportsleaguemanagement.statistics where player_id='"+playerID+"' and type='batting';");
                     rs1.next();
                     rs2.next();
                     rs3.next();
                     rs4.next();
                     BallsLabelValue .setText(rs1.getString("result"));
                     RunsLabelValue  .setText(rs2.getString("result"));
                     SixsLabelValue  .setText(rs3.getString("result"));
                     FoursLabelValue .setText(rs4.getString("result"));
                     try {
                         StrikeRateLabelValue.setText(String.valueOf((float) Integer.parseInt(rs2.getString("result")) * 100 / Integer.parseInt(rs1.getString("result"))));
                     }catch(NumberFormatException e){
                         StrikeRateLabelValue.setText("0");
                     }
                     gridPane.add(StatsLabel                         , 0, 0, 1, 1);
                             gridPane.add(BallsLabel                         , 0, 1, 1, 1);
                             gridPane.add(RunsLabel                          , 0, 2, 1, 1);
                             gridPane.add(SixsLabel                          , 0, 3, 1, 1);
                             gridPane.add(FoursLabel                         , 0, 4, 1, 1);
                             gridPane.add(StrikeRateLabel                         , 0, 5, 1, 1);
                             gridPane.add(BallsLabelValue                        , 1, 1, 1, 1);
                             gridPane.add(RunsLabelValue                        , 1, 2, 1, 1);
                             gridPane.add(SixsLabelValue                        , 1, 3, 1, 1);
                             gridPane.add(FoursLabelValue                        , 1, 4, 1, 1);
                             gridPane.add(StrikeRateLabelValue                        , 1, 5, 1, 1);


                             mainVbox.getChildren().add(gridPane);

                }else if(resultSet.getString("player_type").equals("bowler")){
                    GridPane gridPane = new GridPane();
                    gridPane.setVgap(15);
                    gridPane.setHgap(15);
                    Label OversLabel     = new Label("Overs Bowled")           ;
                    Label RunsLabel      = new Label("Runs")                   ;
                    Label WideBallsLabel = new Label("Wide Balls")             ;
                    Label WicketsLabel   = new Label("Wickets")                ;
                    Label MaidensLabel   = new Label("Maidens")                ;
                    Label DotsLabel      = new Label("Dots")                   ;
                    Label EconomyLabel      = new Label("Economy Rating")                   ;
                    Label OversLabelValue        = new Label("Overs Bowled")           ;
                    Label RunsLabelValue        = new Label("Runs")                   ;
                    Label WideBallsLabelValue    = new Label("Wide Balls")             ;
                    Label WicketsLabelValue      = new Label("Wickets")                ;
                    Label MaidensLabelValue        = new Label("Maidens")                ;
                    Label DotsLabelValue         = new Label("Dots")                   ;
                    Label EconomyLabelValue      = new Label("Economy Rating")                   ;

                    ResultSet rs1 = con.createStatement().executeQuery("SELECT SUM(stat_overs) as result from sportsleaguemanagement.statistics where player_id='"+playerID+"' and type='bowling';");
                    ResultSet rs2 = con.createStatement().executeQuery("SELECT SUM(stat_runs) as  result from sportsleaguemanagement.statistics where player_id='"+playerID+"' and type='bowling';");
                    ResultSet rs3 = con.createStatement().executeQuery("SELECT SUM(stat_wide_balls) as  result from sportsleaguemanagement.statistics where player_id='"+playerID+"' and type='bowling';");
                    ResultSet rs4 = con.createStatement().executeQuery("SELECT SUM(stat_wickets) as  result from sportsleaguemanagement.statistics where player_id='"+playerID+"' and type='bowling';");
                    ResultSet rs5 = con.createStatement().executeQuery("SELECT SUM(stat_maidens) as  result from sportsleaguemanagement.statistics where player_id='"+playerID+"' and type='bowling';");
                    ResultSet rs6 = con.createStatement().executeQuery("SELECT SUM(stat_0s) as  result from sportsleaguemanagement.statistics where player_id='"+playerID+"' and type='bowling';");
                    rs1.next();
                    rs2.next();
                    rs3.next();
                    rs4.next();
                    rs5.next();
                    rs6.next();
                     OversLabelValue      .setText(rs1.getString("result"));
                     RunsLabelValue       .setText(rs2.getString("result"));
                     WideBallsLabelValue  .setText(rs3.getString("result"));
                     WicketsLabelValue    .setText(rs4.getString("result"));
                     MaidensLabelValue    .setText(rs5.getString("result"));
                     DotsLabelValue       .setText(rs6.getString("result"));
                     try {
                         EconomyLabelValue.setText(String.valueOf((float) Integer.parseInt(rs2.getString("result")) / Integer.parseInt(rs1.getString("result"))));
                     }catch(NumberFormatException e){
                          OversLabelValue      .setText("0");
                          RunsLabelValue       .setText("0");
                          WideBallsLabelValue  .setText("0");
                          WicketsLabelValue    .setText("0");
                          MaidensLabelValue    .setText("0");
                          DotsLabelValue       .setText("0");

                         EconomyLabelValue.setText("0");

                    }
                            gridPane.add(StatsLabel                         , 0, 0, 1, 1); 
                            gridPane.add( OversLabel                       , 0, 1, 1, 1);
                            gridPane.add( RunsLabel                        , 0, 2, 1, 1);
                            gridPane.add( WideBallsLabel                   , 0, 3, 1, 1);
                            gridPane.add( WicketsLabel                     , 0, 4, 1, 1);
                            gridPane.add( MaidensLabel                     , 0, 5, 1, 1);
                            gridPane.add( DotsLabel                        , 0, 6, 1, 1);
                            gridPane.add( EconomyLabel                     , 0, 7, 1, 1);
                            gridPane.add( OversLabelValue                  , 1, 1, 1, 1);
                            gridPane.add( RunsLabelValue                   , 1, 2, 1, 1);
                            gridPane.add( WideBallsLabelValue              , 1, 3, 1, 1);
                            gridPane.add( WicketsLabelValue                , 1, 4, 1, 1);
                            gridPane.add( MaidensLabelValue                , 1, 5, 1, 1);
                            gridPane.add( DotsLabelValue                   , 1, 6, 1, 1);
                            gridPane.add( EconomyLabelValue                   , 1, 7, 1, 1);





                              mainVbox.getChildren().add(gridPane);


                }
            }else if(verification_status.equals("rejected")){
                //FOR REJECTED PLAYERS
                mainVbox.getChildren().clear();
                Button RegistrationButton = new Button("Register");
                RegistrationButton.setOnAction((new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        try {
                            SceneSwitcher.switchTo(this.getClass(), e, "PlayerRegistrationForm.fxml","ui/stylesheets/main.css");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }}));
                Label informationLabel = new Label("Your Verification request was denied");
                informationLabel.setStyle("-fx-text-fill:black");
                mainVbox.getChildren().add(informationLabel);
                Label reasonLabel = new Label();
                ResultSet rs10 = con.createStatement().executeQuery("select reason from player where username ='"+LoginSingleton.getInstance().username+"'");
                rs10.next();
                if(rs10.getString("reason")!=null){
                reasonLabel.setText(rs10.getString("reason"));
                    mainVbox.getChildren().add(reasonLabel);
            }

                mainVbox.getChildren().add(RegistrationButton);
                mainVbox.setAlignment(Pos.CENTER);
                mainVbox.setSpacing(50);

            }else{
                //PENDING PLAYERS
                Label informationLabel = new Label("Welcome! "+LoginSingleton.getInstance().username+" ");
                informationLabel.setStyle("-fx-text-fill:black");
                informationLabel.setStyle("-fx-font-size:32");
                mainVbox.getChildren().add(informationLabel);
                mainVbox.setAlignment(Pos.CENTER);
                mainVbox.setSpacing(50);
            }

        }else{
            //FOR FIRST TIME USERS (UNREGISTERED PLAYERS)
            mainVbox.getChildren().clear();
            Button RegistrationButton = new Button("Register");
            RegistrationButton.setOnAction((new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        SceneSwitcher.switchTo(this.getClass(), e, "PlayerRegistrationForm.fxml","ui/stylesheets/main.css");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }}));

            Label informationLabel = new Label("Welcome! "+LoginSingleton.getInstance().username+" ");
            informationLabel.setStyle("-fx-text-fill:black");
            informationLabel.setStyle("-fx-font-size:32");
            mainVbox.getChildren().add(informationLabel);
            mainVbox.getChildren().add(RegistrationButton);
            mainVbox.setAlignment(Pos.CENTER);
            mainVbox.setSpacing(50);

        }

    }







    @FXML
    private void logout(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "LoginScreen.fxml","ui/stylesheets/LoginScreenStyleSheet.css");
    }
    @FXML
    private void viewPlayerRegistrationForm(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "PlayerRegistrationForm.fxml","ui/stylesheets/main.css");
    } @FXML
    private void viewHomeScreen(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "PlayerScreen.fxml","ui/stylesheets/main.css");
    }
    @FXML
    private void viewPlayerTournamentList(ActionEvent event) throws IOException {

        SceneSwitcher.switchTo(this.getClass(), event, "PlayerTournamentList.fxml","ui/stylesheets/main.css");
    }
    @FXML
    private void viewTeamInvites(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "PlayerTeamInvites.fxml","ui/stylesheets/main.css");
    }

}
