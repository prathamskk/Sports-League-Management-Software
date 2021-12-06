package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.model.DatabaseConnector;
import com.project.sportsleaguemanagementproject.model.StatsTable;
import com.project.sportsleaguemanagementproject.singleton.MatchIdSingleton;
import com.project.sportsleaguemanagementproject.singleton.SceneSwitcher;
import com.project.sportsleaguemanagementproject.singleton.TournamentTableButtonClickSingleton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

    private final TableView<StatsTable> table = createTable();
    private static final int rowsPerPage = 5;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            //TODO ADD STATS FORM
            con = DatabaseConnector.getConnection();
            pagination.setPageFactory(this::createPage);
        }catch(SQLException ex){
            Logger.getLogger(AdminTournamentListController.class.getName()).log(Level.SEVERE, null , ex);
        }
    }

    private Node createPage(int pageIndex) {
        this.createData(pageIndex);
        return table;
    }

    private void createData(int pageIndex) {
        try {
            ResultSet rs = con.createStatement().executeQuery("select * from statistics where match_id='"+id+"' LIMIT " + (rowsPerPage * pageIndex ) + ", " + rowsPerPage + ";");
            table.getItems().clear();
            while (rs.next()) {
                ResultSet rs1 = con.createStatement().executeQuery("select * from player where player_id='"+rs.getInt("player_id")+"';");
                rs1.next();
                table.getItems().addAll(
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
                                addButton(rs.getRow(), rs.getInt("stat_id"))//this is just view button
                        )

                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(AdminTournamentListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private TableView<StatsTable> createTable(){
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
         button
         );
        return table;
    }

    private Button addButton(int rowNumber, int tournament_id) {//this is just view button

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
