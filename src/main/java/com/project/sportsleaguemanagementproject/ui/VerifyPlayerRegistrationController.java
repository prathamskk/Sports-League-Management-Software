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
    @FXML
    private Pagination pagination;
    @FXML
    private TableView<ModelUnverifiedPlayers> verifyPlayerRegistrationTable;
    @FXML
    private TableColumn<ModelUnverifiedPlayers , String> usernameColumn;
    @FXML
    private TableColumn<ModelUnverifiedPlayers , String> aadharNoColumn;
    @FXML
    private TableColumn<ModelUnverifiedPlayers , String> nameColumn;
    @FXML
    private TableColumn<ModelUnverifiedPlayers , String> genderColumn;
    @FXML
    private TableColumn<ModelUnverifiedPlayers , Date> dobColumn;
    @FXML
    private TableColumn<ModelUnverifiedPlayers , Float> weightColumn;
    @FXML
    private TableColumn<ModelUnverifiedPlayers , Float> heightColumn;
    @FXML
    private TableColumn<ModelUnverifiedPlayers , String> playerTypeColumn;
    @FXML
    private TableColumn<ModelUnverifiedPlayers , Button> buttonsColumn;

    ObservableList<ModelUnverifiedPlayers> oblist = FXCollections.observableArrayList();

    private final int rowsPerPage = 10;
    private int pageNumber = 0;

    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, oblist.size());
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        aadharNoColumn.setCellValueFactory(new PropertyValueFactory<>("aadharNo"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        playerTypeColumn.setCellValueFactory(new PropertyValueFactory<>("playerType"));
        buttonsColumn.setCellValueFactory(new PropertyValueFactory<>("button"));
        verifyPlayerRegistrationTable.setItems(
                FXCollections.observableArrayList(
                        oblist.subList(
                                fromIndex,
                                toIndex
                        )
                )
        );

        return new AnchorPane(verifyPlayerRegistrationTable);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            //adding sql data to table
            Connection con = DatabaseConnector.getConnection();
            ResultSet rs = con.createStatement().executeQuery("select * from player where verification_status = 'pending'");

            while (rs.next()){
                oblist.add(
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
        }catch(SQLException ex){
            Logger.getLogger(TournamentListController.class.getName()).log(Level.SEVERE, null , ex);
        }

        pagination = new Pagination((oblist.size() / rowsPerPage + 1), 0);
        pagination.setPageFactory(this::createPage);

        verifyPlayerRegistrationTable.setItems(oblist);
    }

    Button addButtonToOBList(int rowNumber){
        Button ret = new Button();
        ret.setId(String.valueOf(rowNumber));
        ret.setText(String.valueOf(rowNumber));
        return ret;
    }
}
