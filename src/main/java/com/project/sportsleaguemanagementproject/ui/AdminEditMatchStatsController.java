package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.model.DatabaseConnector;
import com.project.sportsleaguemanagementproject.model.StatsTable;
import com.project.sportsleaguemanagementproject.singleton.MatchIdSingleton;
import com.project.sportsleaguemanagementproject.singleton.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminEditMatchStatsController implements Initializable {
    private final int id = MatchIdSingleton.getInstance().id;
    private Connection con;
    @FXML
    private Pagination pagination;
    @FXML
    private VBox mainPane;

    private final TableView<StatsTable> table1 = createTable1();
    private final TableView<StatsTable> table2 = createTable2();
    private final TableView<StatsTable> table3 = createTable3();
    private final TableView<StatsTable> table4 = createTable4();
  
    private final VBox table1container = new VBox();
    private final VBox table2container = new VBox();
    private final VBox table3container = new VBox();
    private final VBox table4container = new VBox();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            //TODO ADD STATS FORM
            //TODO EDIT TABLE COLUMNS
            con = DatabaseConnector.getConnection();
            createData1();//Team 1 Batting
            createData2();//Team 2 Bowling
            createData3();//Team 2 Batting
            createData4();//Team 1 Bowling
            table1container.getChildren().add(table1);
            table2container.getChildren().add(table2);
            table3container.getChildren().add(table3);
            table4container.getChildren().add(table4);
            table1container.setMinHeight(400);
            table2container.setMinHeight(400);
            table3container.setMinHeight(400);
            table4container.setMinHeight(400);
            table1container.getStyleClass().add("main-pane");
            table2container.getStyleClass().add("main-pane");
            table3container.getStyleClass().add("main-pane");
            table4container.getStyleClass().add("main-pane");
            mainPane.getChildren().add(table1container);
            mainPane.getChildren().add(table2container);
            mainPane.getChildren().add(table3container);
            mainPane.getChildren().add(table4container);

        }catch(SQLException ex){
            Logger.getLogger(AdminTournamentListController.class.getName()).log(Level.SEVERE, null , ex);
        }
    }


    private void createData1() {
        try {
            ResultSet rs = con.createStatement().executeQuery("select * from statistics where stat_id=(select stat_id from match_innings where match_id='"+id+"' and innings_id='1' and type='batting') ;");
            table1.getItems().clear();
            while (rs.next()) {
                ResultSet rs1 = con.createStatement().executeQuery("select * from player where player_id='"+rs.getInt("player_id")+"';");
                rs1.next();
                table1.getItems().addAll(
                        new StatsTable(rs.getInt("stat_id"),
                                rs1.getInt("aadhar_no"),
                                rs1.getString("name"),
                                rs.getInt("stat_balls"),
                                rs.getInt("stat_runs"),
                                rs.getInt("stat_overs"),
                                rs.getInt("stat_6s"),
                                rs.getInt("stat_4s"),
                                rs.getInt("stat_wide_balls"),
                                rs.getInt("stat_wickets"),
                                rs.getInt("stat_maidens"),
                                rs.getInt("stat_0s"),
                                addButton(rs.getRow(), rs.getInt("stat_id"))//this is just view button
                        )

                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(AdminTournamentListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void createData2() {
        try {
            ResultSet rs = con.createStatement().executeQuery("select * from statistics where stat_id=(select stat_id from match_innings where match_id='"+id+"' and innings_id='1' and type='bowling') ;");
            table2.getItems().clear();
            while (rs.next()) {
                ResultSet rs1 = con.createStatement().executeQuery("select * from player where player_id='"+rs.getInt("player_id")+"';");
                rs1.next();
                table2.getItems().addAll(
                        new StatsTable(rs.getInt("stat_id"),
                                rs1.getInt("aadhar_no"),
                                rs1.getString("name"),
                                rs.getInt("stat_balls"),
                                rs.getInt("stat_runs"),
                                rs.getInt("stat_overs"),
                                rs.getInt("stat_6s"),
                                rs.getInt("stat_4s"),
                                rs.getInt("stat_wide_balls"),
                                rs.getInt("stat_wickets"),
                                rs.getInt("stat_maidens"),
                                rs.getInt("stat_0s"),
                                addButton(rs.getRow(), rs.getInt("stat_id"))//this is just view button
                        )

                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(AdminTournamentListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void createData3() {
        try {
            ResultSet rs = con.createStatement().executeQuery("select * from statistics where stat_id=(select stat_id from match_innings where match_id='"+id+"' and innings_id='2' and type='batting') ;");
            table3.getItems().clear();
            while (rs.next()) {
                ResultSet rs1 = con.createStatement().executeQuery("select * from player where player_id='"+rs.getInt("player_id")+"';");
                rs1.next();
                table3.getItems().addAll(
                        new StatsTable(rs.getInt("stat_id"),
                                rs1.getInt("aadhar_no"),
                                rs1.getString("name"),
                                rs.getInt("stat_balls"),
                                rs.getInt("stat_runs"),
                                rs.getInt("stat_overs"),
                                rs.getInt("stat_6s"),
                                rs.getInt("stat_4s"),
                                rs.getInt("stat_wide_balls"),
                                rs.getInt("stat_wickets"),
                                rs.getInt("stat_maidens"),
                                rs.getInt("stat_0s"),
                                addButton(rs.getRow(), rs.getInt("stat_id"))//this is just view button
                        )

                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(AdminTournamentListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void createData4() {
        try {
            ResultSet rs = con.createStatement().executeQuery("select * from statistics where stat_id=(select stat_id from match_innings where match_id='"+id+"' and innings_id='2' and type='bowling') ;");
            table4.getItems().clear();
            while (rs.next()) {
                ResultSet rs1 = con.createStatement().executeQuery("select * from player where player_id='"+rs.getInt("player_id")+"';");
                rs1.next();
                table4.getItems().addAll(
                        new StatsTable(rs.getInt("stat_id"),
                                rs1.getInt("aadhar_no"),
                                rs1.getString("name"),
                                rs.getInt("stat_balls"),
                                rs.getInt("stat_runs"),
                                rs.getInt("stat_overs"),
                                rs.getInt("stat_6s"),
                                rs.getInt("stat_4s"),
                                rs.getInt("stat_wide_balls"),
                                rs.getInt("stat_wickets"),
                                rs.getInt("stat_maidens"),
                                rs.getInt("stat_0s"),
                                addButton(rs.getRow(), rs.getInt("stat_id"))//this is just view button
                        )

                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(AdminTournamentListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private TableView<StatsTable> createTable1(){
        TableView<StatsTable> table = new TableView<>();

        TableColumn<StatsTable , String> stat_id           =      new TableColumn<>("Stat ID");
        TableColumn<StatsTable , String> aadhar_no         =      new TableColumn<>("Aadhar No.");
        TableColumn<StatsTable , String> name              =      new TableColumn<>("Name");
        TableColumn<StatsTable , String> stat_balls        =      new TableColumn<>("Balls");
        TableColumn<StatsTable , String> stat_runs         =      new TableColumn<>("Runs");
        TableColumn<StatsTable , String> stat_overs        =      new TableColumn<>("Overs");
        TableColumn<StatsTable , String> stat_6s           =      new TableColumn<>("6s");
        TableColumn<StatsTable , String> stat_4s           =      new TableColumn<>("4s");
        TableColumn<StatsTable , String> stat_wide_balls   =      new TableColumn<>("Wide Balls");
        TableColumn<StatsTable , String> stat_wickets      =      new TableColumn<>("Wickets");
        TableColumn<StatsTable , String> stat_maidens      =      new TableColumn<>("Maidens");
        TableColumn<StatsTable , String> stat_0s           =      new TableColumn<>("Dots");
        TableColumn<StatsTable , Button> button            =      new TableColumn<>("");
  /*
        stat_id         .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        aadhar_no       .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        name            .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_balls      .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_runs       .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_overs      .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_6s         .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_4s         .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_wide_balls .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_wickets    .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_maidens    .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        button          .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
    */
        stat_id         .setCellValueFactory(new PropertyValueFactory<>("stat_id"));
        aadhar_no       .setCellValueFactory(new PropertyValueFactory<>("aadhar_no"));
        name            .setCellValueFactory(new PropertyValueFactory<>("name"));
        stat_balls      .setCellValueFactory(new PropertyValueFactory<>("stat_balls"));
        stat_runs       .setCellValueFactory(new PropertyValueFactory<>("stat_runs"));
        stat_overs      .setCellValueFactory(new PropertyValueFactory<>("stat_overs"));
        stat_6s         .setCellValueFactory(new PropertyValueFactory<>("stat_6s"));
        stat_4s         .setCellValueFactory(new PropertyValueFactory<>("stat_4s"));
        stat_wide_balls .setCellValueFactory(new PropertyValueFactory<>("stat_wide_balls"));
        stat_wickets    .setCellValueFactory(new PropertyValueFactory<>("stat_wickets"));
        stat_maidens    .setCellValueFactory(new PropertyValueFactory<>("stat_maidens"));
        stat_0s         .setCellValueFactory(new PropertyValueFactory<>("stat_0s"));
        button          .setCellValueFactory(new PropertyValueFactory<>("button"));








        table.getColumns().addAll(
         stat_id            ,
         aadhar_no          ,
         name               ,
         stat_balls         ,
         stat_runs          ,
         stat_overs         ,
         stat_6s            ,
         stat_4s            ,
         stat_wide_balls    ,
         stat_wickets       ,
         stat_maidens       ,
         stat_0s,
         button
         );
        return table;
    }

    private TableView<StatsTable> createTable2(){
        TableView<StatsTable> table = new TableView<>();

        TableColumn<StatsTable , String> stat_id           =      new TableColumn<>("Stat ID");
        TableColumn<StatsTable , String> aadhar_no         =      new TableColumn<>("Aadhar No.");
        TableColumn<StatsTable , String> name              =      new TableColumn<>("Name");
        TableColumn<StatsTable , String> stat_balls        =      new TableColumn<>("Balls");
        TableColumn<StatsTable , String> stat_runs         =      new TableColumn<>("Runs");
        TableColumn<StatsTable , String> stat_overs        =      new TableColumn<>("Overs");
        TableColumn<StatsTable , String> stat_6s           =      new TableColumn<>("6s");
        TableColumn<StatsTable , String> stat_4s           =      new TableColumn<>("4s");
        TableColumn<StatsTable , String> stat_wide_balls   =      new TableColumn<>("Wide Balls");
        TableColumn<StatsTable , String> stat_wickets      =      new TableColumn<>("Wickets");
        TableColumn<StatsTable , String> stat_maidens      =      new TableColumn<>("Maidens");
        TableColumn<StatsTable , String> stat_0s           =      new TableColumn<>("Dots");
        TableColumn<StatsTable , Button> button            =      new TableColumn<>("");
  /*
        stat_id         .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        aadhar_no       .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        name            .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_balls      .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_runs       .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_overs      .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_6s         .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_4s         .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_wide_balls .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_wickets    .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_maidens    .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        button          .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
    */
        stat_id         .setCellValueFactory(new PropertyValueFactory<>("stat_id"));
        aadhar_no       .setCellValueFactory(new PropertyValueFactory<>("aadhar_no"));
        name            .setCellValueFactory(new PropertyValueFactory<>("name"));
        stat_balls      .setCellValueFactory(new PropertyValueFactory<>("stat_balls"));
        stat_runs       .setCellValueFactory(new PropertyValueFactory<>("stat_runs"));
        stat_overs      .setCellValueFactory(new PropertyValueFactory<>("stat_overs"));
        stat_6s         .setCellValueFactory(new PropertyValueFactory<>("stat_6s"));
        stat_4s         .setCellValueFactory(new PropertyValueFactory<>("stat_4s"));
        stat_wide_balls .setCellValueFactory(new PropertyValueFactory<>("stat_wide_balls"));
        stat_wickets    .setCellValueFactory(new PropertyValueFactory<>("stat_wickets"));
        stat_maidens    .setCellValueFactory(new PropertyValueFactory<>("stat_maidens"));
        stat_0s         .setCellValueFactory(new PropertyValueFactory<>("stat_0s"));
        button          .setCellValueFactory(new PropertyValueFactory<>("button"));








        table.getColumns().addAll(
                stat_id            ,
                aadhar_no          ,
                name               ,
                stat_balls         ,
                stat_runs          ,
                stat_overs         ,
                stat_6s            ,
                stat_4s            ,
                stat_wide_balls    ,
                stat_wickets       ,
                stat_maidens       ,
                stat_0s,
                button
        );
        return table;
    }

    private TableView<StatsTable> createTable3(){
        TableView<StatsTable> table = new TableView<>();

        TableColumn<StatsTable , String> stat_id           =      new TableColumn<>("Stat ID");
        TableColumn<StatsTable , String> aadhar_no         =      new TableColumn<>("Aadhar No.");
        TableColumn<StatsTable , String> name              =      new TableColumn<>("Name");
        TableColumn<StatsTable , String> stat_balls        =      new TableColumn<>("Balls");
        TableColumn<StatsTable , String> stat_runs         =      new TableColumn<>("Runs");
        TableColumn<StatsTable , String> stat_overs        =      new TableColumn<>("Overs");
        TableColumn<StatsTable , String> stat_6s           =      new TableColumn<>("6s");
        TableColumn<StatsTable , String> stat_4s           =      new TableColumn<>("4s");
        TableColumn<StatsTable , String> stat_wide_balls   =      new TableColumn<>("Wide Balls");
        TableColumn<StatsTable , String> stat_wickets      =      new TableColumn<>("Wickets");
        TableColumn<StatsTable , String> stat_maidens      =      new TableColumn<>("Maidens");
        TableColumn<StatsTable , String> stat_0s           =      new TableColumn<>("Dots");
        TableColumn<StatsTable , Button> button            =      new TableColumn<>("");
  /*
        stat_id         .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        aadhar_no       .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        name            .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_balls      .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_runs       .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_overs      .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_6s         .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_4s         .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_wide_balls .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_wickets    .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_maidens    .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        button          .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
    */
        stat_id         .setCellValueFactory(new PropertyValueFactory<>("stat_id"));
        aadhar_no       .setCellValueFactory(new PropertyValueFactory<>("aadhar_no"));
        name            .setCellValueFactory(new PropertyValueFactory<>("name"));
        stat_balls      .setCellValueFactory(new PropertyValueFactory<>("stat_balls"));
        stat_runs       .setCellValueFactory(new PropertyValueFactory<>("stat_runs"));
        stat_overs      .setCellValueFactory(new PropertyValueFactory<>("stat_overs"));
        stat_6s         .setCellValueFactory(new PropertyValueFactory<>("stat_6s"));
        stat_4s         .setCellValueFactory(new PropertyValueFactory<>("stat_4s"));
        stat_wide_balls .setCellValueFactory(new PropertyValueFactory<>("stat_wide_balls"));
        stat_wickets    .setCellValueFactory(new PropertyValueFactory<>("stat_wickets"));
        stat_maidens    .setCellValueFactory(new PropertyValueFactory<>("stat_maidens"));
        stat_0s         .setCellValueFactory(new PropertyValueFactory<>("stat_0s"));
        button          .setCellValueFactory(new PropertyValueFactory<>("button"));








        table.getColumns().addAll(
                stat_id            ,
                aadhar_no          ,
                name               ,
                stat_balls         ,
                stat_runs          ,
                stat_overs         ,
                stat_6s            ,
                stat_4s            ,
                stat_wide_balls    ,
                stat_wickets       ,
                stat_maidens       ,
                stat_0s,
                button
        );
        return table;
    }

    private TableView<StatsTable> createTable4(){
        TableView<StatsTable> table = new TableView<>();

        TableColumn<StatsTable , String> stat_id           =      new TableColumn<>("Stat ID");
        TableColumn<StatsTable , String> aadhar_no         =      new TableColumn<>("Aadhar No.");
        TableColumn<StatsTable , String> name              =      new TableColumn<>("Name");
        TableColumn<StatsTable , String> stat_balls        =      new TableColumn<>("Balls");
        TableColumn<StatsTable , String> stat_runs         =      new TableColumn<>("Runs");
        TableColumn<StatsTable , String> stat_overs        =      new TableColumn<>("Overs");
        TableColumn<StatsTable , String> stat_6s           =      new TableColumn<>("6s");
        TableColumn<StatsTable , String> stat_4s           =      new TableColumn<>("4s");
        TableColumn<StatsTable , String> stat_wide_balls   =      new TableColumn<>("Wide Balls");
        TableColumn<StatsTable , String> stat_wickets      =      new TableColumn<>("Wickets");
        TableColumn<StatsTable , String> stat_maidens      =      new TableColumn<>("Maidens");
        TableColumn<StatsTable , String> stat_0s           =      new TableColumn<>("Dots");
        TableColumn<StatsTable , Button> button            =      new TableColumn<>("");
  /*
        stat_id         .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        aadhar_no       .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        name            .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_balls      .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_runs       .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_overs      .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_6s         .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_4s         .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_wide_balls .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_wickets    .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        stat_maidens    .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
        button          .prefWidthProperty().bind(table.prefWidthProperty().divide(100/15));
    */
        stat_id         .setCellValueFactory(new PropertyValueFactory<>("stat_id"));
        aadhar_no       .setCellValueFactory(new PropertyValueFactory<>("aadhar_no"));
        name            .setCellValueFactory(new PropertyValueFactory<>("name"));
        stat_balls      .setCellValueFactory(new PropertyValueFactory<>("stat_balls"));
        stat_runs       .setCellValueFactory(new PropertyValueFactory<>("stat_runs"));
        stat_overs      .setCellValueFactory(new PropertyValueFactory<>("stat_overs"));
        stat_6s         .setCellValueFactory(new PropertyValueFactory<>("stat_6s"));
        stat_4s         .setCellValueFactory(new PropertyValueFactory<>("stat_4s"));
        stat_wide_balls .setCellValueFactory(new PropertyValueFactory<>("stat_wide_balls"));
        stat_wickets    .setCellValueFactory(new PropertyValueFactory<>("stat_wickets"));
        stat_maidens    .setCellValueFactory(new PropertyValueFactory<>("stat_maidens"));
        stat_0s         .setCellValueFactory(new PropertyValueFactory<>("stat_0s"));
        button          .setCellValueFactory(new PropertyValueFactory<>("button"));








        table.getColumns().addAll(
                stat_id            ,
                aadhar_no          ,
                name               ,
                stat_balls         ,
                stat_runs          ,
                stat_overs         ,
                stat_6s            ,
                stat_4s            ,
                stat_wide_balls    ,
                stat_wickets       ,
                stat_maidens       ,
                stat_0s,
                button
        );
        return table;
    }

    private Button addButton(int rowNumber, int stat_id) {//this is just view button

        Button ret = new Button();
        ret.setId(String.valueOf(rowNumber));
        ret.setText("EDIT");
        ret.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
           }
        });

        return ret;
    }


    @FXML
    private Button logoutButton;


    @FXML
    private void logout(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "LoginScreen.fxml","ui/stylesheets/LoginScreenStyleSheet.css");
    }
    @FXML
    private void viewAdminTournamentCreator(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "AdminTournamentCreator.fxml","ui/stylesheets/main.css");
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
