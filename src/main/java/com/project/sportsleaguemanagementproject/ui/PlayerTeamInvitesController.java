package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.model.DatabaseConnector;
import com.project.sportsleaguemanagementproject.model.PlayerTeamInvitesTable;
import com.project.sportsleaguemanagementproject.singleton.LoginSingleton;
import com.project.sportsleaguemanagementproject.singleton.SceneSwitcher;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerTeamInvitesController implements Initializable {
    private final String username = LoginSingleton.getInstance().username;
    private Connection con;
    @FXML
    private Pagination pagination;
    private int teamId = -1;
    @FXML
    private Label notifyLabel;
    @FXML
    private Label teamNameLabel;
    @FXML
    private Label notifyTeamExistence;
    @FXML
    private Label currentTeamNameLabel;
    @FXML
    private Button leaveTeamButton;

    private final TableView<PlayerTeamInvitesTable> table = createTable();
    private static final int rowsPerPage = 20;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            con = DatabaseConnector.getConnection();
            pagination.setPageFactory(this::createPage);
            checkForTeam();
            if(teamId==-1){
                notifyTeamExistence.setText("No Team");
                notifyTeamExistence.setVisible(true);
                teamNameLabel.setVisible(false);
                currentTeamNameLabel.setVisible(false);
                leaveTeamButton.setVisible(false);
            }else {
                ResultSet rs = con.createStatement().executeQuery("select team_name from team where team_id = '"+teamId+"';");
                rs.next();
                notifyTeamExistence.setVisible(false);
                currentTeamNameLabel.setText(rs.getString("team_name"));
            }
        }catch(SQLException ex){
            Logger.getLogger(PlayerTeamInvitesTable.class.getName()).log(Level.SEVERE, null , ex);
        }
    }
    @FXML
    private void handleLeaveTeamButton(ActionEvent event) throws SQLException, IOException {
        con.createStatement().executeUpdate("Update player set team_id=null where username = '"+username+"';");
        notifyTeamExistence.setText("Team Left successfully");
        notifyTeamExistence.setVisible(true);
        teamNameLabel.setVisible(false);
        currentTeamNameLabel.setVisible(false);
        leaveTeamButton.setVisible(false);
        SceneSwitcher.switchTo(this.getClass(), event, "PlayerTeamInvites.fxml","ui/stylesheets/PlayerTeamInvitesController.css");


    }

    private void checkForTeam() throws SQLException {
        ResultSet rs = con.createStatement().executeQuery("select team_id from player where username = '"+username+"';");
        if (rs.next()){
            if(rs.getInt("team_id")!=0){
                teamId = rs.getInt("team_id");

            }

        }
    }

    private Node createPage(int pageIndex) {
        this.createData(pageIndex);
        return table;
    }

    private void createData(int pageIndex) {
        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT team_name from team where team_id in (select team_id from team_request_player where player_id in (select player_id from player where username = '"+username+"')) LIMIT " + (rowsPerPage * pageIndex ) + ", " + rowsPerPage + ";");
            table.getItems().clear();
            while (rs.next()) {
                table.getItems().addAll(new PlayerTeamInvitesTable(rs.getString("team_name"),
                                                                   addAcceptButton(rs.getRow(),rs.getString("team_name")),
                                                                   addRejectButton(rs.getRow(),rs.getString("team_name"))
                        ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(PlayerTournamentListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private TableView<PlayerTeamInvitesTable> createTable(){
        TableView<PlayerTeamInvitesTable> table = new TableView<>();

        TableColumn<PlayerTeamInvitesTable , String> col_teamName = new TableColumn<>("teamName");
        TableColumn<PlayerTeamInvitesTable, Button> col_acceptButton = new TableColumn<>("acceptButton");
        TableColumn<PlayerTeamInvitesTable , Button> col_declineButton = new TableColumn<>("declineButton");

        col_teamName.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        col_acceptButton.setCellValueFactory(new PropertyValueFactory<>("acceptButton"));
        col_declineButton.setCellValueFactory(new PropertyValueFactory<>("declineButton"));

        table.getColumns().addAll( col_teamName,
                col_acceptButton,
                col_declineButton);
        return table;
    }

    private Button addAcceptButton(int rowNumber, String team_name) {

        Button ret = new Button();
        String accept = "acceptButton";
        ret.setId(accept+rowNumber);
        ret.setText(String.valueOf(rowNumber));
        ret.setOnAction(e -> {
            if(teamId==-1){
                try {
                    con.createStatement().executeUpdate("update player set team_id = (select team_id from team where team_name = '"+team_name+"') where username = '"+username+"'; ");
                    con.createStatement().executeUpdate("DELETE FROM team_request_player WHERE player_id=(select player_id from player where username='"+username+"') and team_id =(select team_id from team where team_name = '"+team_name+"');");
                    SceneSwitcher.switchTo(this.getClass(), e, "PlayerTeamInvites.fxml","ui/stylesheets/PlayerTeamInvitesController.css");

                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }
            }else {
                notifyLabel.setText("Leave your Existing team to join another team");
            }

        });

        return ret;
    }


    private Button addRejectButton(int rowNumber, String team_name) {

        Button ret = new Button();
        String reject= "rejectButton";
        ret.setId(reject+rowNumber);
        ret.setText(String.valueOf(team_name));
        ret.setOnAction(e -> {
            try {
                con.createStatement().executeUpdate("DELETE FROM team_request_player WHERE player_id=(select player_id from player where username='"+username+"') and team_id =(select team_id from team where team_name = '"+team_name+"');");
                pagination.setPageFactory(this::createPage);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            notifyLabel.setText("Successfully declined");
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
