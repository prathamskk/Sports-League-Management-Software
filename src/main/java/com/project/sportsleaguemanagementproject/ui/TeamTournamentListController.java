package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.model.DatabaseConnector;
import com.project.sportsleaguemanagementproject.model.TeamTournamentListTable;
import com.project.sportsleaguemanagementproject.singleton.LoginSingleton;
import com.project.sportsleaguemanagementproject.singleton.SceneSwitcher;
import com.project.sportsleaguemanagementproject.singleton.TournamentTableButtonClickSingleton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TeamTournamentListController implements Initializable {
    private final String username = LoginSingleton.getInstance().username;
    private Connection con;
    private final TableView<TeamTournamentListTable> table = createTable();
    private static final int rowsPerPage = 20;
    @FXML
    private Pagination pagination;
    @FXML
    private Label notifyLabel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            con = DatabaseConnector.getConnection();
            pagination.setPageFactory(this::createPage);
        }catch(SQLException ex){
            Logger.getLogger(TeamTournamentListTable.class.getName()).log(Level.SEVERE, null , ex);
        }
    }
    private Node createPage(int pageIndex) {
        this.createData(pageIndex);
        return table;
    }

    private void createData(int pageIndex) {
        try {


            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM tournament WHERE registration_date >= CURDATE() LIMIT " + (rowsPerPage * pageIndex ) + ", " + rowsPerPage + ";");
            table.getItems().clear();
            while (rs.next()) {
                int noOfTeamsRegistered=0;
                ResultSet resultSet = con.createStatement().executeQuery("select team_id from teams_in_tournament where tournament_id='"+rs.getInt("tournament_id")+"';");
                while (resultSet.next()){
                    noOfTeamsRegistered++;

                }
                table.getItems().addAll(new TeamTournamentListTable(
                        rs.getString("tournament_name"),
                        rs.getInt("tournament_prize"),
                        rs.getDate("registration_date"),
                        rs.getString("venue"),
                        noOfTeamsRegistered+"/"+rs.getInt("max_teams"),
                        addViewButton(rs.getRow(), rs.getInt("tournament_id")),
                        addJoinButton(rs.getRow(), rs.getInt("tournament_id"),noOfTeamsRegistered,rs.getInt("max_teams"))
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(TeamTournamentListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private TableView<TeamTournamentListTable> createTable(){
        TableView<TeamTournamentListTable> table = new TableView<>();

        TableColumn<TeamTournamentListTable,String> col_name = new TableColumn<>("name");
        TableColumn<TeamTournamentListTable,String> col_prize = new TableColumn<>("prize");
        TableColumn<TeamTournamentListTable, Date>   col_registration  = new TableColumn<>  ("date");
         TableColumn<TeamTournamentListTable,String> col_venue = new TableColumn<>("venue");
         TableColumn<TeamTournamentListTable,String> col_maxTeams = new TableColumn<>("maxTeams");
         TableColumn<TeamTournamentListTable , Button> viewButtonColumn  = new TableColumn<>("viewButton");
          TableColumn<TeamTournamentListTable , Button> joinButtonColumn  = new TableColumn<>("joinButton");



        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_prize.setCellValueFactory(new PropertyValueFactory<>("prize"));
        col_registration.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_venue.setCellValueFactory(new PropertyValueFactory<>("venue"));
        col_maxTeams.setCellValueFactory(new PropertyValueFactory<>("maxTeams"));
        viewButtonColumn.setCellValueFactory(new PropertyValueFactory<>("viewButton"));
        joinButtonColumn.setCellValueFactory(new PropertyValueFactory<>("joinButton"));

        table.getColumns().addAll( col_name,
                 col_prize,
                 col_registration,
                 col_venue,
                 col_maxTeams,
                 viewButtonColumn,
                 joinButtonColumn
        );
        return table;
    }

    private Button addViewButton(int rowNumber, int tournament_id) {

        Button ret = new Button();
        ret.setId("view"+rowNumber);
        ret.setText(String.valueOf(rowNumber));
        ret.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                TournamentTableButtonClickSingleton.getInstance().id = tournament_id;
                try {
                    SceneSwitcher.switchTo(this.getClass(), e, "TeamViewTournamentDetails.fxml");//TODO
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        return ret;
    }


    private Button addJoinButton(int rowNumber, int tournament_id,int noOfTeamsRegistered,int max_teams) {
        notifyLabel.setText("");

        Button ret = new Button();
        ret.setId("join"+rowNumber);
        ret.setText(String.valueOf(tournament_id));
        ret.setOnAction(e -> {
            try {
                ResultSet rs = con.createStatement().executeQuery("select team_id from teams_in_tournament where team_id in(select team_id from team where username ='"+username+"') and tournament_id = '"+tournament_id+"'; ");
                if(rs.next()){
                    notifyLabel.setText("You are Already Registered for this tournament");
                }else {
                    if(noOfTeamsRegistered<max_teams){
                        con.createStatement().executeUpdate("insert into teams_in_tournament values((select team_id from team where username = '"+username+"'),'"+tournament_id+"');");
                        pagination.setPageFactory(this::createPage);
                        notifyLabel.setText("Successfully Registered");
                    }else {
                        notifyLabel.setText("Registration Full");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
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
    private void viewTeamRequestPlayerJoinForm(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "TeamRequestPlayerJoinForm.fxml");
    }
    @FXML
    private void viewTeamTournamentList(ActionEvent event) throws IOException {

        SceneSwitcher.switchTo(this.getClass(), event, "TeamTournamentList.fxml");
    }
}
