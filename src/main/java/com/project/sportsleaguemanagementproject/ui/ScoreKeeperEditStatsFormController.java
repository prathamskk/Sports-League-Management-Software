package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.model.DatabaseConnector;
import com.project.sportsleaguemanagementproject.singleton.ImageLoader;
import com.project.sportsleaguemanagementproject.singleton.LoginSingleton;
import com.project.sportsleaguemanagementproject.singleton.SceneSwitcher;
import com.project.sportsleaguemanagementproject.singleton.StatIdSingleton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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

public class ScoreKeeperEditStatsFormController implements Initializable {
    @FXML
    private VBox mainVbox;
    @FXML
    private Label notifyLabel;
    private Connection con;
    private final int id = StatIdSingleton.getInstance().id;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            userIcon.setFill(new ImagePattern(ImageLoader.getInstance().loadImage()));
            accountNameLabel.setText(LoginSingleton.getInstance().username);
            accountNameLabel.getStyleClass().add("account-label");
            jobLabel.setText("Score Keeper");
            jobLabel.getStyleClass().add("job-label"); con = DatabaseConnector.getConnection();
            fillData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private void fillData() throws SQLException {
        ResultSet rs = con.createStatement().executeQuery("select innings_id,type from statistics where stat_id='"+id+"';");
        ResultSet rs1 = con.createStatement().executeQuery("select * from statistics where stat_id='"+id+"';");
        rs.next();
        if(rs.getString("type").equals("batting")){
            fillBattingData(rs1);
        }else{
            fillBowlingData(rs1);
        }
    }
    private void fillBattingData(ResultSet rs1) throws SQLException {
        rs1.next();
        String playerID = rs1.getString("player_id");
        ResultSet rs2 = con.createStatement().executeQuery("select * from player where player_id='"+playerID+"'");
        rs2.next();
        GridPane gridPane = new GridPane();
        gridPane.setVgap(15);
        gridPane.setHgap(15);
        Label AadharNoLabel  = new Label("Aadhar No") ;
        Label BallsLabel     = new Label("Balls")     ;
        Label RunsLabel      = new Label("Runs")      ;
        Label SixsLabel      = new Label("6s")        ;
        Label FoursLabel     = new Label("4s")        ;
        TextField AadharNoTF = new TextField(rs2.getString("aadhar_no"   ) );
        TextField BallsTF    = new TextField(rs1.getString("stat_balls"  ) );
        TextField RunsTF     = new TextField(rs1.getString("stat_runs"   ) );
        TextField SixsTF     = new TextField(rs1.getString("stat_6s"     ) );
        TextField FoursTF    = new TextField(rs1.getString("stat_4s"     ) );
        Button EditButton    = new Button("Submit");
        EditButton.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    if(AadharNoTF.getText().equals("")||BallsTF.getText().equals("")||RunsTF.getText().equals("")||SixsTF.getText().equals("")||FoursTF.getText().equals("")){
                        notifyLabel.setText("Fields cannot be empty");
                    }else {
                       ResultSet rs3 = con.createStatement().executeQuery("select player_id from player where aadhar_no='"+ AadharNoTF.getText()+"';");
                       if(rs3.next()) {
                           con.createStatement().executeUpdate("Update statistics set player_id='" + rs3.getString("player_id") + "',stat_balls='" + BallsTF.getText() + "',stat_runs='" + RunsTF.getText() + "',stat_6s='" + SixsTF.getText() + "',stat_4s='" + FoursTF.getText() + "' where stat_id='" + id + "'");
                           notifyLabel.setText("Successfully Updated");
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

        mainVbox.getChildren().add(gridPane);

    }
    private void fillBowlingData(ResultSet rs1) throws SQLException {
        rs1.next();
        String playerID = rs1.getString("player_id");
        ResultSet rs2 = con.createStatement().executeQuery("select * from player where player_id='"+playerID+"'");
        rs2.next(); 
        GridPane gridPane = new GridPane();
        gridPane.setVgap(15);
        gridPane.setHgap(15);
        Label AadharNoLabel  = new Label("Aadhar No")              ;
        Label OversLabel     = new Label("Overs Bowled")           ;
        Label RunsLabel      = new Label("Runs")                   ;
        Label WideBallsLabel = new Label("Wide Balls")             ;
        Label WicketsLabel   = new Label("Wickets")                ;
        Label MaidensLabel   = new Label("Maidens")                ;
        Label DotsLabel      = new Label("Dots")                   ;
        TextField AadharNoTF     = new TextField(rs2.getString("aadhar_no")        );
        TextField OversTF        = new TextField(rs1.getString("stat_overs")       );
        TextField RunsTF         = new TextField(rs1.getString("stat_runs")        );
        TextField WideBallsTF    = new TextField(rs1.getString("stat_wide_balls")  );
        TextField WicketsTF      = new TextField(rs1.getString("stat_wickets")     );
        TextField MaidensTF      = new TextField(rs1.getString("stat_maidens")     );
        TextField DotsTF         = new TextField(rs1.getString("stat_0s")          );
        Button EditButton    = new Button("Submit");
        EditButton.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    if(AadharNoTF.getText().equals("")||OversTF.getText().equals("")||RunsTF.getText().equals("")||WideBallsTF.getText().equals("")||WicketsTF.getText().equals("")||MaidensTF.getText().equals("")||DotsTF.getText().equals("")){
                        notifyLabel.setText("Fields cannot be empty");
                    }else {
                        ResultSet rs3 = con.createStatement().executeQuery("select player_id from player where aadhar_no='"+ AadharNoTF.getText()+"';");
                        if(rs3.next()) {
                            con.createStatement().executeUpdate("Update statistics set player_id='" + rs3.getString("player_id") + "',stat_overs='" + OversTF.getText() + "',stat_runs='" + RunsTF.getText() + "',stat_wide_balls='" + WideBallsTF.getText() + "',stat_wickets='" + WicketsTF.getText() + "',stat_maidens='" + MaidensTF.getText() + "',stat_0s='" + DotsTF.getText() + "' where stat_id='" + id + "'");
                            notifyLabel.setText("Successfully Updated");
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

        mainVbox.getChildren().add(gridPane);

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
    @FXML
    private void viewHomeScreen(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "ScoreKeeperScreen.fxml","ui/stylesheets/main.css");
    }

}
