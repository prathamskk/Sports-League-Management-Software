package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.MainApplication;
import com.project.sportsleaguemanagementproject.model.DatabaseConnector;
import com.project.sportsleaguemanagementproject.model.ModelTournamentList;
import com.project.sportsleaguemanagementproject.singleton.ImageLoader;
import com.project.sportsleaguemanagementproject.singleton.LoginSingleton;
import com.project.sportsleaguemanagementproject.singleton.SceneSwitcher;
import com.project.sportsleaguemanagementproject.singleton.TournamentTableButtonClickSingleton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScoreKeeperScreenController implements Initializable {
    @FXML
    private Circle userIcon;
    @FXML
    private Label accountNameLabel;
    @FXML
    private Label jobLabel;
    private Connection con;
    @FXML
    private Pagination pagination;

    private final TableView<ModelTournamentList> table = createTable();
    private static final int rowsPerPage = 5;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userIcon.setFill(new ImagePattern(ImageLoader.getInstance().loadImage()));
        accountNameLabel.setText(LoginSingleton.getInstance().username);
            accountNameLabel.getStyleClass().add("account-label");
        jobLabel.setText("Score Keeper");
        try {
            con = DatabaseConnector.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pagination.setPageFactory(this::createPage);
    }

    private Node createPage(int pageIndex) {
        this.createData(pageIndex);
        return table;
    }

    private void createData(int pageIndex) {
        try {
            ResultSet rs = con.createStatement().executeQuery("select * from tournament WHERE registration_date >= CURDATE() LIMIT " + (rowsPerPage * pageIndex ) + ", " + rowsPerPage + ";");
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
            Logger.getLogger(ScoreKeeperTournamentListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private TableView<ModelTournamentList> createTable(){
        TableView<ModelTournamentList> table = new TableView<>();

        TableColumn<ModelTournamentList , String> col_id = new TableColumn<>("id");
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
        col_maxTeams.prefWidthProperty().bind(table.widthProperty().divide(100/12));
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

    private Button addButton(int rowNumber, int tournament_id) {

        Button ret = new Button();
        ret.setId(String.valueOf(rowNumber));
        ret.setText("VIEW");
        ret.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                TournamentTableButtonClickSingleton.getInstance().id = tournament_id;
                try {
                    SceneSwitcher.switchTo(this.getClass(), e, "ScoreKeeperViewTournamentDetails.fxml","ui/stylesheets/main.css");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        return ret;
    }


    @FXML
    private void logout(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "LoginScreen.fxml","ui/stylesheets/LoginScreenStyleSheet.css");
    }
    @FXML
    private void viewTournamentList(ActionEvent event) throws IOException {

        SceneSwitcher.switchTo(this.getClass(), event, "ScoreKeeperTournamentList.fxml","ui/stylesheets/main.css");
    }
    @FXML
    private void viewHomeScreen(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "ScoreKeeperScreen.fxml","ui/stylesheets/main.css");
    }
}
