package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.model.DatabaseConnector;
import com.project.sportsleaguemanagementproject.model.ModelTournamentList;

import com.project.sportsleaguemanagementproject.singleton.TournamentTableButtonClickSingleton;
import com.project.sportsleaguemanagementproject.singleton.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminTournamentListController implements Initializable {
    private Connection con;
    @FXML
    private Pagination pagination;

    private final TableView<ModelTournamentList> table = createTable();
    private static final int rowsPerPage = 5;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
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
            ResultSet rs = con.createStatement().executeQuery("select * from tournament LIMIT " + (rowsPerPage * pageIndex ) + ", " + rowsPerPage + ";");
            table.getItems().clear();
            while (rs.next()) {
                int noOfTeamsRegistered=0;
                ResultSet resultSet = con.createStatement().executeQuery("select team_id from teams_in_tournament where tournament_id='"+rs.getInt("tournament_id")+"';");
                while (resultSet.next()){
                    noOfTeamsRegistered++;

                }
                table.getItems().addAll(
                                new ModelTournamentList(rs.getInt("tournament_id"),
                                        rs.getString("tournament_name"),
                                        rs.getInt("tournament_prize"),
                                        rs.getDate("registration_date"),
                                        rs.getString("venue"),
                                        noOfTeamsRegistered+"/"+rs.getInt("max_teams"),
                                        addButton(rs.getRow(), rs.getInt("tournament_id"))//this is just view button
                                )

             );
            }

        } catch (SQLException ex) {
            Logger.getLogger(AdminTournamentListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private TableView<ModelTournamentList> createTable(){
        TableView<ModelTournamentList> table = new TableView<>();

        TableColumn<ModelTournamentList , String> col_id = new TableColumn<>("Id");
        TableColumn<ModelTournamentList , String> col_name = new TableColumn<>("Tournament Name");
        TableColumn<ModelTournamentList , String> col_prize = new TableColumn<>("Prize");
        TableColumn<ModelTournamentList , Date>   col_registration  = new TableColumn<>  ("Final Reg. Date");
        TableColumn<ModelTournamentList,String> col_venue = new TableColumn<>("Venue");
        TableColumn<ModelTournamentList,String> col_maxTeams = new TableColumn<>("No of Teams");
        TableColumn<ModelTournamentList , Button> buttonsColumn  = new TableColumn<>("");

        col_id.prefWidthProperty().bind(table.widthProperty().divide(100/6));
        col_name.prefWidthProperty().bind(table.widthProperty().divide(100/20));
        col_prize.prefWidthProperty().bind(table.widthProperty().divide(100/10));
        col_registration.prefWidthProperty().bind(table.widthProperty().divide(100/15));
        col_venue.prefWidthProperty().bind(table.widthProperty().divide(100/20));
        col_maxTeams.prefWidthProperty().bind(table.widthProperty().divide(100/13));
        buttonsColumn.prefWidthProperty().bind(table.widthProperty().divide(100/12));



        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_prize.setCellValueFactory(new PropertyValueFactory<>("prize"));
        col_registration.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_venue.setCellValueFactory(new PropertyValueFactory<>("venue"));
        col_maxTeams.setCellValueFactory(new PropertyValueFactory<>("maxTeams"));
        buttonsColumn.setCellValueFactory(new PropertyValueFactory<> ("button"));
        table.getColumns().addAll( col_id,
                                    col_name  ,
                                   col_prize  ,
                                   col_registration,
                                      col_venue,
                                     col_maxTeams,
                                   buttonsColumn
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
                TournamentTableButtonClickSingleton.getInstance().id = tournament_id;
                try {
                    SceneSwitcher.switchTo(this.getClass(), e, "AdminEditTournamentDetails.fxml","ui/stylesheets/main.css");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
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

