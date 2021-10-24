package com.project.sportsleaguemanagementproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class LoginScreenController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label messageDisplay;
    @FXML
    private Button btnlogin;
    @FXML
    private Button btnregister;
    @FXML
    private TextField edtusername;
    @FXML
    private PasswordField edtpassword;



    @FXML
    private void loadDefaultScreen(ActionEvent event) throws IOException
    {
        root = FXMLLoader.load(getClass().getResource("defaultScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void loadAdminScreen(ActionEvent event) throws IOException
    {
        root = FXMLLoader.load(getClass().getResource("adminScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void loadScorekeeperScreen(ActionEvent event) throws IOException
    {
        root = FXMLLoader.load(getClass().getResource("scorekeeperScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    @FXML
    public void CheckLoginPerformed(ActionEvent e)

    {


        if (e.getSource() == btnlogin)

        {
            try {
                String data = null;
                try {
                    File myObj = new File("D:\\COLLEGE NOTES\\java stuff\\SportsLeagueManagementProject\\src\\main\\java\\com\\project\\sportsleaguemanagementproject\\data.txt");
                    Scanner myReader = new Scanner(myObj);
                    while (myReader.hasNextLine()) {
                        data = myReader.nextLine();
                    }
                    myReader.close();
                } catch (FileNotFoundException e2) {
                    System.out.println("An error occurred.");
                    e2.printStackTrace();
                }


                String username = edtusername.getText();
                String password = edtpassword.getText();

                if(username == "")
                {
                    messageDisplay.setText("username field is empty");
                }
                else if(password == "")
                {
                    messageDisplay.setText("password field is empty");
                }
                else {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sportsleaguemanagement", "root", data);
                    Statement statement = connection.createStatement();
                    String sql = "select * from logininfo where username='" + username + "'and password='" + password + "'";
                    ResultSet resultSet = statement.executeQuery(sql);

                    if (resultSet.next()) {
                        String temp = resultSet.getString("type");
                        String admin = "admin";
                        String scorekeeper = "scorekeeper";

                        if (temp.equals(admin)) {
                            loadAdminScreen(e);
                        } else if (temp.equals(scorekeeper)) {
                            loadScorekeeperScreen(e);
                        } else {
                            loadDefaultScreen(e);
                        }
                    } else {
                        System.out.println("WRONG COMBO");
                        messageDisplay.setText("Wrong username and password combination");
                        edtusername.setText("");
                        edtpassword.setText("");
                    }
                }

            } catch (Exception exception)
            {
                exception.printStackTrace();
            }

        }

        if (e.getSource() == btnregister) {

            try
            {
                String data = null;
                try {
                    File myObj = new File("D:\\COLLEGE NOTES\\java stuff\\SportsLeagueManagementProject\\src\\main\\java\\com\\project\\sportsleaguemanagementproject\\data.txt");
                    Scanner myReader = new Scanner(myObj);
                    while (myReader.hasNextLine()) {
                        data = myReader.nextLine();
                    }
                    myReader.close();
                } catch (FileNotFoundException e2) {
                    System.out.println("An error occurred.");
                    e2.printStackTrace();
                }

                String username = edtusername.getText();
                String password = edtpassword.getText();
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sportsleaguemanagement", "root", data);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from logininfo where username='" + username + "'");
                if (resultSet.next())
                {
                    System.out.println("USERNAME ALREADY EXISTS");
                    messageDisplay.setText("Username already exists");
                    edtusername.setText("");
                    edtpassword.setText("");
                }

                else if(username == "")
                {
                    messageDisplay.setText("username field is empty");
                }
                else if(password == "")
                {
                    messageDisplay.setText("password field is empty");
                }
                else
                {
                    String sql = "INSERT INTO logininfo (username,password) VALUES ('" + username + "' , '" + password + "')";
                    statement.executeUpdate(sql);
                    System.out.println("REGISTRATION SUCCESSFUL");
                    messageDisplay.setText("Registration successful");

                }

            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }

}