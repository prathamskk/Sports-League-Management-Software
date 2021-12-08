package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.model.DatabaseConnector;
import com.project.sportsleaguemanagementproject.singleton.ImageLoader;
import com.project.sportsleaguemanagementproject.singleton.LoginSingleton;
import com.project.sportsleaguemanagementproject.singleton.MatchIdSingleton;
import com.project.sportsleaguemanagementproject.singleton.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ScoreKeeperAddStatsFormController implements Initializable {
    private final int id = MatchIdSingleton.getInstance().id;
    private Connection con;
    @FXML
    private VBox formVbox;
    @FXML
    private Label notifyLabel;
    @FXML
    private ChoiceBox<String> TypeChoiceBox;
    @FXML
    private ChoiceBox<String> inningsChoiceBox;
    @FXML
    private Button generateFormButton;
    private final String[] TypeArray = {"batting","bowling"};
    private final String[] inningsArray ={"1","2"};


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            userIcon.setFill(new ImagePattern(ImageLoader.getInstance().loadImage()));
            accountNameLabel.setText(LoginSingleton.getInstance().username);
            jobLabel.setText("Score Keeper");
            con = DatabaseConnector.getConnection();
            TypeChoiceBox     .getItems().addAll(TypeArray)     ;
            inningsChoiceBox  .getItems().addAll(inningsArray)  ;
            TypeChoiceBox     .getSelectionModel().select(0)    ;
            inningsChoiceBox  .getSelectionModel().select(0)    ;

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGenerateFormButton(){
        notifyLabel.setText("");
        formVbox.getChildren().clear();
    GridPane gridPane = new GridPane();
    if(TypeChoiceBox.getValue().equals("batting"))   {
        Label AadharNoLabel  = new Label("Aadhar No") ;
        Label BallsLabel     = new Label("Balls")     ;
        Label RunsLabel      = new Label("Runs")      ;
        Label SixsLabel      = new Label("6s")        ;
        Label FoursLabel     = new Label("4s")        ;
        TextField AadharNoTF = new TextField();
        TextField BallsTF    = new TextField();
        TextField RunsTF     = new TextField();
        TextField SixsTF     = new TextField();
        TextField FoursTF    = new TextField();
        Button EditButton    = new Button("Add");
        EditButton.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    if(AadharNoTF.getText().equals("")||BallsTF.getText().equals("")||RunsTF.getText().equals("")||SixsTF.getText().equals("")||FoursTF.getText().equals("")){
                        notifyLabel.setText("Fields cannot be empty");
                    }else {
                        ResultSet rs3 = con.createStatement().executeQuery("select player_id from player where aadhar_no='"+ AadharNoTF.getText()+"';");
                        if(rs3.next()) {
                            con.createStatement().executeUpdate("insert into statistics (match_id,innings_id,type,player_id,stat_balls,stat_runs,stat_6s,stat_4s) values('"+id+"','"+inningsChoiceBox.getValue()+"','"+TypeChoiceBox.getValue()+"','" + rs3.getString("player_id") + "','" + BallsTF.getText() + "','" + RunsTF.getText() + "','" + SixsTF.getText() + "','" + FoursTF.getText() + "' );");
                            notifyLabel.setText("Successfully Added");
                        }else{
                            notifyLabel.setText("Player with given Aadhar No. Doesn't Exist");
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    notifyLabel.setText("Enter Numbers only");
                }
            }
        }));


        gridPane.add(AadharNoLabel                      , 0, 0, 1, 1);
        gridPane.add(BallsLabel                         , 0, 1, 1, 1);
        gridPane.add(RunsLabel                          , 0, 2, 1, 1);
        gridPane.add(SixsLabel                          , 0, 3, 1, 1);
        gridPane.add(FoursLabel                         , 0, 4, 1, 1);
        gridPane.add(AadharNoTF                         , 1, 0, 1, 1);
        gridPane.add(BallsTF                            , 1, 1, 1, 1);
        gridPane.add(RunsTF                             , 1, 2, 1, 1);
        gridPane.add(SixsTF                             , 1, 3, 1, 1);
        gridPane.add(FoursTF                            , 1, 4, 1, 1);
        gridPane.add(EditButton                            , 1, 5, 1, 1);

        formVbox.getChildren().add(gridPane);

    }else{
        Label AadharNoLabel  = new Label("Aadhar No")              ;
        Label OversLabel     = new Label("Overs Bowled")           ;
        Label RunsLabel      = new Label("Runs")                   ;
        Label WideBallsLabel = new Label("Wide Balls")             ;
        Label WicketsLabel   = new Label("Wickets")                ;
        Label MaidensLabel   = new Label("Maidens")                ;
        Label DotsLabel      = new Label("Dots")                   ;
        TextField AadharNoTF     = new TextField();
        TextField OversTF        = new TextField();
        TextField RunsTF         = new TextField();
        TextField WideBallsTF    = new TextField();
        TextField WicketsTF      = new TextField();
        TextField MaidensTF      = new TextField();
        TextField DotsTF         = new TextField();
        Button EditButton    = new Button("Add");

        EditButton.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    if(AadharNoTF.getText().equals("")||OversTF.getText().equals("")||RunsTF.getText().equals("")||WideBallsTF.getText().equals("")||WicketsTF.getText().equals("")||MaidensTF.getText().equals("")||DotsTF.getText().equals("")){
                        notifyLabel.setText("Fields cannot be empty");
                    }else {
                        ResultSet rs3 = con.createStatement().executeQuery("select player_id from player where aadhar_no='"+ AadharNoTF.getText()+"';");
                        if(rs3.next()) {
                            con.createStatement().executeUpdate("insert into statistics (match_id,innings_id,type,player_id,stat_overs,stat_runs,stat_wide_balls,stat_wickets,stat_maidens,stat_0s) values ('"+id+"','"+inningsChoiceBox.getValue()+"','"+TypeChoiceBox.getValue()+"','"+rs3.getString("player_id")+"','" + OversTF.getText() + "','" + RunsTF.getText() + "','" + WideBallsTF.getText() + "','" + WicketsTF.getText() + "','" + MaidensTF.getText() + "','" + DotsTF.getText() + "') ;");
                            notifyLabel.setText("Successfully Added");
                        }else{
                            notifyLabel.setText("Player with given Aadhar No. Doesn't Exist");
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    notifyLabel.setText("Enter Numbers only");
                }
            }
        }));

        gridPane.add(AadharNoLabel      , 0, 0, 1, 1);
        gridPane.add(OversLabel         , 0, 1, 1, 1);
        gridPane.add(RunsLabel          , 0, 2, 1, 1);
        gridPane.add(WideBallsLabel     , 0, 3, 1, 1);
        gridPane.add(WicketsLabel       , 0, 4, 1, 1);
        gridPane.add(MaidensLabel       , 0, 5, 1, 1);
        gridPane.add(DotsLabel          , 0, 6, 1, 1);
        gridPane.add(AadharNoTF         , 1, 0, 1, 1);
        gridPane.add(OversTF            , 1, 1, 1, 1);
        gridPane.add(RunsTF             , 1, 2, 1, 1);
        gridPane.add(WideBallsTF        , 1, 3, 1, 1);
        gridPane.add(WicketsTF          , 1, 4, 1, 1);
        gridPane.add(MaidensTF          , 1, 5, 1, 1);
        gridPane.add(DotsTF             , 1, 6, 1, 1);
        gridPane.add(EditButton                            , 1, 7, 1, 1);

        formVbox.getChildren().add(gridPane);

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
    private void viewTournamentList(ActionEvent event) throws IOException {

        SceneSwitcher.switchTo(this.getClass(), event, "ScoreKeeperTournamentList.fxml","ui/stylesheets/main.css");
    }
}
