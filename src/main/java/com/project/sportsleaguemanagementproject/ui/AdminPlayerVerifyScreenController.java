package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.model.DatabaseConnector;
import com.project.sportsleaguemanagementproject.singleton.AdminPendingPlayerTableButtonClickSingleton;
import com.project.sportsleaguemanagementproject.singleton.ImageLoader;
import com.project.sportsleaguemanagementproject.singleton.LoginSingleton;
import com.project.sportsleaguemanagementproject.singleton.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminPlayerVerifyScreenController implements Initializable {
    @FXML
    private Button acceptButton;
    @FXML
    private Button rejectButton;
    @FXML
    private TextArea denyReasonTextArea;

    @FXML
    private Label usernameLabel;
    @FXML
    private Label aadharNoLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label genderLabel;
    @FXML
    private Label DOBLabel;
    @FXML
    private Label weightLabel;
    @FXML
    private Label heightLabel;
    @FXML
    private Label playerTypeLabel;
    @FXML
    private Label notifyLabel;


    private final String id = AdminPendingPlayerTableButtonClickSingleton.getInstance().id;
    private Connection con;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            userIcon.setFill(new ImagePattern(ImageLoader.getInstance().loadImage()));
            accountNameLabel.setText(LoginSingleton.getInstance().username);
            accountNameLabel.getStyleClass().add("account-label");
            jobLabel.setText("Admin");
            jobLabel.getStyleClass().add("job-label");
            con = DatabaseConnector.getConnection();
            fillData();
        }catch(SQLException ex){
            Logger.getLogger(AdminTournamentListController.class.getName()).log(Level.SEVERE, null , ex);
        }
    }
    private void fillData() throws SQLException {
        ResultSet rs = con.createStatement().executeQuery("select * from player where username='"+id+"';");
        rs.next();
        usernameLabel.setText(rs.getString  ("username"));
        aadharNoLabel.setText(rs.getString("aadhar_no"));
        nameLabel.setText(rs.getString("name"));
        genderLabel.setText(rs.getString("gender"));
        DOBLabel.setText(rs.getString("dob"));
        weightLabel.setText(rs.getString("weight"));
        heightLabel.setText(rs.getString("height"));
        playerTypeLabel.setText(rs.getString("player_type"));
     }
    @FXML
     private void handleAcceptButton(ActionEvent event) throws SQLException, IOException {
        con.createStatement().executeUpdate("update player set verification_status = 'verified'  WHERE username = '" + id + "';");
        SceneSwitcher.switchTo(this.getClass(), event, "AdminPendingPlayerList.fxml", "ui/stylesheets/main.css");
    }
     @FXML
    private void handleRejectButton(ActionEvent event) throws SQLException {
        String reason = denyReasonTextArea.getText();
        if(reason.equals("")){
            notifyLabel.setText("Please enter Reason");

        }else{
            con.createStatement().executeUpdate("update player set verification_status = 'rejected' , reason = '"+reason+"' WHERE username = '"+id+"';");
        }
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
    } @FXML
    private void viewHomeScreen(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "AdminScreen.fxml","ui/stylesheets/main.css");
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
