package com.project.sportsleaguemanagementproject.ui;

import com.project.sportsleaguemanagementproject.player.PlayerGender;
import com.project.sportsleaguemanagementproject.player.PlayerType;
import com.project.sportsleaguemanagementproject.singleton.LoginSingleton;
import com.project.sportsleaguemanagementproject.model.DatabaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistrationPlayerScreenController implements Initializable {

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


    // TODO: 11/9/2021 switch to enum
    private final String[] genders = PlayerGender.toArray();
    private final String[] playerTypes = PlayerType.toArray();
    private String selectedGender;
    private String selectedPlayerType;
    private final String username = LoginSingleton.getInstance().username;

    private void getGender(ActionEvent event){
        selectedGender = genderchoicebox.getValue();

    }
    private void getPlayerType(ActionEvent event){
        selectedPlayerType = player_typechoicebox.getValue();

    }

    @FXML
    private void submitDetails(ActionEvent e){
        try {
            Connection con = DatabaseConnector.getConnection();
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
            Logger.getLogger(RegistrationPlayerScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genderchoicebox.getItems().addAll(genders);
        genderchoicebox.setOnAction(this::getGender);
        player_typechoicebox.getItems().addAll(playerTypes);
        player_typechoicebox.setOnAction(this::getPlayerType);

    }
}
