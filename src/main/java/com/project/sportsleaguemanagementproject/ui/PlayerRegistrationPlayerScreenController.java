package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.player.PlayerGender;
import com.project.sportsleaguemanagementproject.player.PlayerType;
import com.project.sportsleaguemanagementproject.singleton.LoginSingleton;
import com.project.sportsleaguemanagementproject.model.DatabaseConnector;
import com.project.sportsleaguemanagementproject.singleton.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerRegistrationPlayerScreenController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private TextField aadharno;
    @FXML
    private DatePicker DOB;
    @FXML
    private TextField weight;
    @FXML
    private TextField height;
    @FXML
    private ChoiceBox<String> genderchoicebox;
    @FXML
    private ChoiceBox<String> player_typechoicebox;
    @FXML
    private VBox MainVbox;



    // TODO: 11/9/2021 switch to enum
    private final String[] genders = PlayerGender.toArray();
    private final String[] playerTypes = PlayerType.toArray();
    private String selectedGender;
    private String selectedPlayerType;
    private final String username = LoginSingleton.getInstance().username;
    private Connection con;

    private void getGender(ActionEvent event){
        selectedGender = genderchoicebox.getValue();

    }
    private void getPlayerType(ActionEvent event){
        selectedPlayerType = player_typechoicebox.getValue();

    }

    @FXML
    private void submitDetails(ActionEvent e){
        try {

            PreparedStatement preparedStatement = con.prepareStatement("insert into player(username,aadhar_no,name,gender,dob,weight,height,player_type) values (? ,?,?,?,?,?,?,?)");
            preparedStatement.setString(1,username);
            preparedStatement.setString(2, aadharno.getText());
            preparedStatement.setString(3,name.getText());
            preparedStatement.setString(4, selectedGender);
            preparedStatement.setDate(5, Date.valueOf(DOB.getValue()));
            preparedStatement.setString(6,weight.getText());
            preparedStatement.setString(7,height.getText());
            preparedStatement.setString(8, selectedPlayerType);
            preparedStatement.executeUpdate();



        } catch (SQLException ex) {
            Logger.getLogger(PlayerRegistrationPlayerScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            con = DatabaseConnector.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(checkAlreadyRegistered()){
                MainVbox.getChildren().clear();
                Label registeredLabel = new Label();
                registeredLabel.setText("Already registered");
                MainVbox.getChildren().add(registeredLabel);
                MainVbox.setAlignment(Pos.CENTER);
            }else{
                genderchoicebox.getItems().addAll(genders);
                genderchoicebox.setOnAction(this::getGender);
                player_typechoicebox.getItems().addAll(playerTypes);
                player_typechoicebox.setOnAction(this::getPlayerType);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private boolean checkAlreadyRegistered() throws SQLException {
       ResultSet rs = con.createStatement().executeQuery("select player_id from player where username = '"+username+"'");
       if(rs.next()){
           return true;
       }else{
           return false;
       }
    }



    @FXML
    private Button logoutButton;


    @FXML
    private void logout(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "LoginScreen.fxml","ui/stylesheets/LoginScreenStyleSheet.css");
    }
    @FXML
    private void viewPlayerRegistrationForm(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "PlayerRegistrationForm.fxml","ui/stylesheets/main.css");
    }
    @FXML
    private void viewPlayerTournamentList(ActionEvent event) throws IOException {

        SceneSwitcher.switchTo(this.getClass(), event, "PlayerTournamentList.fxml","ui/stylesheets/main.css");
    }
    @FXML
    private void viewTeamInvites(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(this.getClass(), event, "PlayerTeamInvites.fxml","ui/stylesheets/main.css");
    }

}
