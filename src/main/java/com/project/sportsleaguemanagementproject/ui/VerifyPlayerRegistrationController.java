package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.model.DatabaseConnector;
import com.project.sportsleaguemanagementproject.model.ModelUnverifiedPlayers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VerifyPlayerRegistrationController implements Initializable {
    private Connection con;
    @FXML
    private Pagination pagination;

    private final TableView<ModelUnverifiedPlayers> verifyPlayerRegistrationTable = createTable();
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
        return verifyPlayerRegistrationTable;
    }

    private void createData(int pageIndex) {
        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM player where verification_status='pending' LIMIT " + (rowsPerPage * pageIndex ) + ", " + rowsPerPage + ";");
            verifyPlayerRegistrationTable.getItems().clear();
            while (rs.next()) {
                verifyPlayerRegistrationTable.getItems().addAll(
                        new ModelUnverifiedPlayers(
                                rs.getString("username"),
                                rs.getInt("aadhar_no"),
                                rs.getString("name"),
                                rs.getString("gender"),
                                rs.getDate("dob"),
                                rs.getFloat("weight"),
                                rs.getFloat("height"),
                                rs.getString("player_type"),
                                addButtonToOBList(rs.getRow()) // TODO: 11/13/2021
                        )
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(TournamentListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

        private TableView<ModelUnverifiedPlayers> createTable(){
            TableView<ModelUnverifiedPlayers> verifyPlayerRegistrationTable = new TableView<>();

             TableColumn<ModelUnverifiedPlayers , String> usernameColumn = new TableColumn<>("username");
             TableColumn<ModelUnverifiedPlayers , String> aadharNoColumn = new TableColumn<>("aadharNo");
             TableColumn<ModelUnverifiedPlayers , String> nameColumn   = new TableColumn<>  ("name");
             TableColumn<ModelUnverifiedPlayers , String> genderColumn = new TableColumn<>  ("gender");
             TableColumn<ModelUnverifiedPlayers , Date>   dobColumn      = new TableColumn<>  ("dob");
             TableColumn<ModelUnverifiedPlayers , Float>  weightColumn   = new TableColumn<> ("weight");
             TableColumn<ModelUnverifiedPlayers , Float>  heightColumn    = new TableColumn<>("height");
             TableColumn<ModelUnverifiedPlayers , String> playerTypeColumn=new TableColumn<>("playerType");
             TableColumn<ModelUnverifiedPlayers , Button> buttonsColumn  = new TableColumn<>("button");

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        aadharNoColumn.setCellValueFactory(new PropertyValueFactory<>("aadharNo"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>    ("name"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>  ("gender"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>     ("dob"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>  ("weight"));
        heightColumn.setCellValueFactory(new PropertyValueFactory<>  ("height"));
        playerTypeColumn.setCellValueFactory(new PropertyValueFactory<>("playerType"));
        buttonsColumn.setCellValueFactory(new PropertyValueFactory<> ("button"));
        verifyPlayerRegistrationTable.getColumns().addAll( usernameColumn,
                                                           aadharNoColumn ,
                                                           nameColumn  ,
                                                           genderColumn ,
                                                           dobColumn ,
                                                           weightColumn ,
                                                           heightColumn ,
                                                           playerTypeColumn,
                                                        buttonsColumn );

        return verifyPlayerRegistrationTable;
    }



    Button addButtonToOBList(int rowNumber){
        Button ret = new Button();
        ret.setId(String.valueOf(rowNumber));
        ret.setText(String.valueOf(rowNumber));
        return ret;
    }
}
