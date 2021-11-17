package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.model.DatabaseConnector;
import com.project.sportsleaguemanagementproject.model.TeamRequestPlayerTable;
import com.project.sportsleaguemanagementproject.singleton.LoginSingleton;
import com.project.sportsleaguemanagementproject.singleton.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TeamRequestPlayerJoinFormController implements Initializable {
    private final String username = LoginSingleton.getInstance().username;
    private Connection con;
    @FXML
    private Pagination pagination;

    private final TableView<TeamRequestPlayerTable> table = createTable();
    private static final int rowsPerPage = 20;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            con = DatabaseConnector.getConnection();
            pagination.setPageFactory(this::createPage);
        }catch(SQLException ex){
            Logger.getLogger(TeamRequestPlayerJoinFormController.class.getName()).log(Level.SEVERE, null , ex);
        }
    }

    private Node createPage(int pageIndex) {
        this.createData(pageIndex);
        return table;
    }

    private void createData(int pageIndex) {
        try {
            ResultSet rs = con.createStatement().executeQuery("select name,aadhar_no from player where player_id in (select player_id from team_request_player where team_id in (select team_id from team where username ='"+username+"')) LIMIT " + (rowsPerPage * pageIndex ) + ", " + rowsPerPage + ";");
            table.getItems().clear();
            while (rs.next()) {
                table.getItems().addAll(new TeamRequestPlayerTable(rs.getInt    ("aadhar_no"),
                                                                   rs.getString ("name")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(PlayerTournamentListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private TableView<TeamRequestPlayerTable> createTable(){
        TableView<TeamRequestPlayerTable> table = new TableView<>();

        TableColumn<TeamRequestPlayerTable , String> col_aadharno = new TableColumn<>("AadharNo");
        TableColumn<TeamRequestPlayerTable, String> col_name = new TableColumn<>("name");

        col_aadharno.setCellValueFactory(new PropertyValueFactory<>("AadharNo"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));

        table.getColumns().addAll( col_aadharno,
                                   col_name);
        return table;
    }




    @FXML
    private Button SendRequestButton;
    @FXML
    private TextField aadharTextField;

    @FXML
    private void handleSendRequestButton(ActionEvent event) {
        String aadharNo = aadharTextField.getText();
        try {
            con.createStatement().executeUpdate("insert into team_request_player(team_id,player_id) values ((select team_id from team where username='"+username+"') ,(select player_id from player where aadhar_no = '"+aadharNo+"'));");

        } catch (SQLException ex) {
            Logger.getLogger(TeamRequestPlayerJoinFormController.class.getName()).log(Level.SEVERE, null, ex);
        }

        pagination.setPageFactory(this::createPage);
    }











    @FXML
    private Button logoutButton;


    @FXML
    private void logout(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "LoginScreen.fxml","ui/stylesheets/LoginScreenStyleSheet.css");
    }
    @FXML
    private void viewTeamRequestPlayerJoinForm(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "TeamRequestPlayerJoinForm.fxml");
    }
    @FXML
    private void viewPlayerTournamentList(ActionEvent event) throws IOException {

        SceneSwitcher.switchTo(this.getClass(), event, "PlayerTournamentList.fxml");
    }
}
