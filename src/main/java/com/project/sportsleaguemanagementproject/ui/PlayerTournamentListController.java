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
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerTournamentListController implements Initializable {
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
            Logger.getLogger(PlayerTournamentListController.class.getName()).log(Level.SEVERE, null , ex);
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
                                        addButton(rs.getRow(), rs.getInt("tournament_id"))
                                )

             );
            }

        } catch (SQLException ex) {
            Logger.getLogger(PlayerTournamentListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private TableView<ModelTournamentList> createTable(){
        TableView<ModelTournamentList> table = new TableView<>();

      //  TableColumn<ModelTournamentList , String> col_id = new TableColumn<>("id");
        TableColumn<ModelTournamentList , String> col_name = new TableColumn<>("name");
        TableColumn<ModelTournamentList , String> col_prize = new TableColumn<>("prize");
        TableColumn<ModelTournamentList , Date>   col_registration  = new TableColumn<>  ("date");
        TableColumn<ModelTournamentList , Button> buttonsColumn  = new TableColumn<>("button");


       // col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_prize.setCellValueFactory(new PropertyValueFactory<>("prize"));
        col_registration.setCellValueFactory(new PropertyValueFactory<>("date"));
        buttonsColumn.setCellValueFactory(new PropertyValueFactory<> ("button"));
        table.getColumns().addAll( //col_id,
                                    col_name  ,
                                   col_prize  ,
                                   col_registration,
                                   buttonsColumn
        );
         return table;
    }

    private Button addButton(int rowNumber, int tournament_id) {

        Button ret = new Button();
        ret.setId(String.valueOf(rowNumber));
        ret.setText(String.valueOf(rowNumber));
        ret.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                TournamentTableButtonClickSingleton.getInstance().id = tournament_id;
                try {
                    SceneSwitcher.switchTo(this.getClass(), e, "PlayerViewTournamentDetails.fxml","ui/stylesheets/PlayerViewTournamentDetailsController.css");
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
    private void viewPlayerRegistrationForm(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "PlayerRegistrationForm.fxml","ui/stylesheets/PlayerRegistrationPlayerScreenController.css");
    }
    @FXML
    private void viewPlayerTournamentList(ActionEvent event) throws IOException {

        SceneSwitcher.switchTo(this.getClass(), event, "PlayerTournamentList.fxml","ui/stylesheets/PlayerTournamentListController.css");
    }
    @FXML
    private void viewTeamInvites(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "PlayerTeamInvites.fxml","ui/stylesheets/PlayerTeamInvitesController.css");
    }

}

