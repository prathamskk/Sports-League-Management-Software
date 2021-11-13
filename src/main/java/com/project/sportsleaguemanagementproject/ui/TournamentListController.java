package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.model.DatabaseConnector;
import com.project.sportsleaguemanagementproject.model.ModelTournamentList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
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
    @FXML
    private TableView<ModelTournamentList > table;
    @FXML
    private TableColumn<ModelTournamentList , String> col_name;
    @FXML
    private TableColumn<ModelTournamentList , String> col_prize;
    @FXML
    private TableColumn<ModelTournamentList , Date> col_registration;

    ObservableList<ModelTournamentList> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{

            //adding sql data to table
            Connection con = DatabaseConnector.getConnection();
            ResultSet rs = con.createStatement().executeQuery("select * from tournament");
            while (rs.next()){
                oblist.add(new ModelTournamentList(rs.getString("tournament_name"),
                        rs.getInt("tournament_prize"),rs.getDate("registration_date")));
            }
        }catch(SQLException ex){
            Logger.getLogger(TournamentListController.class.getName()).log(Level.SEVERE, null , ex);
        }


        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_prize.setCellValueFactory(new PropertyValueFactory<>("prize"));
        col_registration.setCellValueFactory(new PropertyValueFactory<>("date"));

        table.setItems(oblist);
    }
}

