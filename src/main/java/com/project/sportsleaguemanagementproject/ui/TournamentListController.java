package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.model.DatabaseConnector;
import com.project.sportsleaguemanagementproject.model.ModelTournamentList;

import com.project.sportsleaguemanagementproject.singleton.SceneSwitcher;
import javafx.event.ActionEvent;
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

public class TournamentListController implements Initializable {
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
            Logger.getLogger(TournamentListController.class.getName()).log(Level.SEVERE, null , ex);
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
                table.getItems().addAll(
                                new ModelTournamentList(rs.getString("tournament_name"),
                                                        rs.getInt("tournament_prize"),
                                                        rs.getDate("registration_date"),
                                                        addButton(rs.getRow(), rs.getString("tournament_name"))
                                )

             );
            }

        } catch (SQLException ex) {
            Logger.getLogger(TournamentListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private TableView<ModelTournamentList> createTable(){
        TableView<ModelTournamentList> table = new TableView<>();

        TableColumn<ModelTournamentList , String> col_name = new TableColumn<>("name");
        TableColumn<ModelTournamentList , String> col_prize = new TableColumn<>("prize");
        TableColumn<ModelTournamentList , Date>   col_registration  = new TableColumn<>  ("date");
        TableColumn<ModelTournamentList , Button> buttonsColumn  = new TableColumn<>("button");


        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_prize.setCellValueFactory(new PropertyValueFactory<>("prize"));
        col_registration.setCellValueFactory(new PropertyValueFactory<>("date"));
        buttonsColumn.setCellValueFactory(new PropertyValueFactory<> ("button"));
        table.getColumns().addAll( col_name  ,
                                   col_prize  ,
                                   col_registration,
                                   buttonsColumn
        );
         return table;
    }

    private Button addButton(int rowNumber, String tournament_name) {

        Button ret = new Button();
        ret.setId(String.valueOf(rowNumber));
        ret.setText(String.valueOf(rowNumber));
        return ret;
    }


    @FXML
    public Button logoutButton;


    @FXML
    private void logout(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "LoginScreen.fxml","ui/stylesheets/LoginScreenStyleSheet.css");
    }
    @FXML
    private void verifyregisterPlayer(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "VerifyPlayerRegistration.fxml");
    }
    @FXML
    private void viewTournamentList(ActionEvent event) throws IOException {

        SceneSwitcher.switchTo(this.getClass(), event, "TournamentList.fxml");
    }
}

