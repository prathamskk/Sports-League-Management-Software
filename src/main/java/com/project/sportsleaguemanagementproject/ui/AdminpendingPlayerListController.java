package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.model.DatabaseConnector;
import com.project.sportsleaguemanagementproject.model.ModelUnverifiedPlayers;
import com.project.sportsleaguemanagementproject.singleton.AdminPendingPlayerTableButtonClickSingleton;
import com.project.sportsleaguemanagementproject.singleton.ImageLoader;
import com.project.sportsleaguemanagementproject.singleton.LoginSingleton;
import com.project.sportsleaguemanagementproject.singleton.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminpendingPlayerListController implements Initializable {
    private Connection con;
    @FXML
    private Pagination pagination;

    private final TableView<ModelUnverifiedPlayers> PendingPlayerListTable = createTable();
    private static final int rowsPerPage = 5;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            userIcon.setFill(new ImagePattern(ImageLoader.getInstance().loadImage()));
            accountNameLabel.setText(LoginSingleton.getInstance().username);
            jobLabel.setText("Admin");
            con = DatabaseConnector.getConnection();
            pagination.setPageFactory(this::createPage);
        }catch(SQLException ex){
            Logger.getLogger(AdminTournamentListController.class.getName()).log(Level.SEVERE, null , ex);
        }
    }

    private Node createPage(int pageIndex) {
        this.createData(pageIndex);
        return PendingPlayerListTable;
    }

    private void createData(int pageIndex) {
        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM player where verification_status='pending' LIMIT " + (rowsPerPage * pageIndex ) + ", " + rowsPerPage + ";");
            PendingPlayerListTable.getItems().clear();
            while (rs.next()) {
                PendingPlayerListTable.getItems().addAll(
                        new ModelUnverifiedPlayers(
                                rs.getString("username"),
                                rs.getInt("aadhar_no"),
                                rs.getString("name"),
                                rs.getString("gender"),
                                rs.getDate("dob"),
                                rs.getFloat("weight"),
                                rs.getFloat("height"),
                                rs.getString("player_type"),
                                addButtonToOBList(rs.getRow(), rs.getString("username"))
                        )
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(AdminTournamentListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

        private TableView<ModelUnverifiedPlayers> createTable(){
            TableView<ModelUnverifiedPlayers> PendingPlayerListTable = new TableView<>();

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
        PendingPlayerListTable.getColumns().addAll( usernameColumn,
                                                           aadharNoColumn ,
                                                           nameColumn  ,
                                                           genderColumn ,
                                                           dobColumn ,
                                                           weightColumn ,
                                                           heightColumn ,
                                                           playerTypeColumn,
                                                        buttonsColumn );

        return PendingPlayerListTable;
    }



    private Button addButtonToOBList(int rowNumber, String username){

        Button ret = new Button();
        ret.setId(String.valueOf(rowNumber));
        ret.setText(String.valueOf(rowNumber));
        ret.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                AdminPendingPlayerTableButtonClickSingleton.getInstance().id = username;
                try {
                    SceneSwitcher.switchTo(this.getClass(), e, "AdminPlayerVerification.fxml","ui/stylesheets/main.css");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        return ret;
    }


    @FXML
    private Circle userIcon;
    @FXML
    private Label accountNameLabel;
    @FXML
    private Label jobLabel;





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


