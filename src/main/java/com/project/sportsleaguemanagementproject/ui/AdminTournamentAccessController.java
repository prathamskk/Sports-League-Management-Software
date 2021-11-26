package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.model.AdminTournamentAccessTable;
import com.project.sportsleaguemanagementproject.model.DatabaseConnector;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminTournamentAccessController implements Initializable {
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField   passwordTextField;
    @FXML
    private Pagination pagination;
    @FXML
    private Label messageDisplay;
    private Connection con;
    private final int id = TournamentTableButtonClickSingleton.getInstance().id;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pagination.setPageFactory(this::createPage);

        try {
            con = DatabaseConnector.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void handleAddAccountButton() throws SQLException {
        messageDisplay.setText("");
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        ResultSet rs = con.createStatement().executeQuery("select * from account where username='" + username + "'");
        if (rs.next()) {
            System.out.println("USERNAME ALREADY EXISTS");
            messageDisplay.setText("Username already exists");
            usernameTextField.setText("");
            passwordTextField.setText("");
        } else if (username.equals("")) {
            messageDisplay.setText("username field is empty");
        } else if (password.equals("")) {
            messageDisplay.setText("password field is empty");
        } else {
            con.createStatement().executeUpdate("INSERT INTO account (username,password,account_type) VALUES ('" + username + "' , '" + password + "','scorekeeper')");
            System.out.println("REGISTRATION SUCCESSFUL");
            messageDisplay.setText("Registration successful");
            pagination.setPageFactory(this::createPage);

        }
    }

    private final TableView<AdminTournamentAccessTable> table = createTable();
    private static final int rowsPerPage = 20;

    private Node createPage(int pageIndex) {
        this.createData(pageIndex);
        return table;
    }

    private void createData(int pageIndex) {
        String status;
        try {
            ResultSet rs = con.createStatement().executeQuery("select * from account where account_type='scorekeeper' LIMIT " + (rowsPerPage * pageIndex ) + ", " + rowsPerPage + ";");
            table.getItems().clear();
            while (rs.next()) {
                ResultSet resultSet = con.createStatement().executeQuery("select tournament_id from tournament_access where tournament_id = '"+id+"' and username = '"+rs.getString("username")+"' ;");
                if(resultSet.next()){
                    status = "permitted";
                }else{
                    status = "denied";
                }
                table.getItems().addAll(new AdminTournamentAccessTable(    rs.getString("username"),
                        status,addButton(rs.getRow(), rs.getString("username"))));
            }

        } catch (SQLException ex) {
            Logger.getLogger(AdminTournamentAccessController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private TableView<AdminTournamentAccessTable> createTable(){
        TableView<AdminTournamentAccessTable> table = new TableView<>();

        TableColumn<AdminTournamentAccessTable , String> col_username = new TableColumn<>("username");
        TableColumn<AdminTournamentAccessTable , String> col_status = new TableColumn<>("status");
        TableColumn<AdminTournamentAccessTable , Button> col_Button = new TableColumn<>("button");



        col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        col_Button.setCellValueFactory(new PropertyValueFactory<>("button"));

        table.getColumns().addAll( col_username,
                                   col_status,
                                   col_Button
        );
        return table;
    }

    private Button addButton(int rowNumber,String username){
        Button ret = new Button();
        ret.setId(String.valueOf(rowNumber));
        ret.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    System.out.println(id);
                    con.createStatement().executeUpdate("insert into tournament_access values('"+id+"','"+username+"');");

                    SceneSwitcher.switchTo(this.getClass(), e, "AdminTournamentAccess.fxml");
                } catch (SQLException | IOException ex) {
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
    private void viewAdminTournamentCreator(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "AdminTournamentCreator.fxml");
    }
    @FXML
    private void viewPendingPlayerTable(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "PendingPlayerList.fxml");
    }
    @FXML
    private void viewTournamentList(ActionEvent event) throws IOException {

        SceneSwitcher.switchTo(this.getClass(), event, "AdminTournamentList.fxml");
    }
    @FXML
    private void viewPlayerVerification(ActionEvent event) throws IOException {

        SceneSwitcher.switchTo(this.getClass(), event, "AdminPlayerVerification.fxml");
    }
}
