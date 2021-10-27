package com.project.sportsleaguemanagementproject.ui;

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
import java.util.Objects;
import java.util.Scanner;

public class LoginScreenController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label messageDisplay;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;



    @FXML
    private void loadDefaultScreen(ActionEvent event) throws IOException
    {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("defaultScreen.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void loadAdminScreen(ActionEvent event) throws IOException
    {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminScreen.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void loadScorekeeperScreen(ActionEvent event) throws IOException
    {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scorekeeperScreen.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    @FXML
    public void CheckLoginPerformed(ActionEvent e)

    {


        if (e.getSource() == loginButton)

        {
            try {
                String data = null;
                try {
                    File myObj = new File("src/main/resources/com/project/sportsleaguemanagementproject/data.txt");
                    Scanner myReader = new Scanner(myObj);
                    while (myReader.hasNextLine()) {
                        data = myReader.nextLine();
                    }
                    myReader.close();
                } catch (FileNotFoundException e2) {
                    System.out.println("An error occurred.");
                    e2.printStackTrace();
                }


                String username = usernameField.getText();
                String password = passwordField.getText();

                if(username.equals("") || password.equals(""))
                {
                    messageDisplay.setText("username or password field is empty");
                }
                else {


                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sportsleaguemanagement", "root", data);
                    Statement statement = connection.createStatement();
                    String sql = "select * from account where username='" + username + "'and password='" + password + "'";
                    ResultSet resultSet = statement.executeQuery(sql);

                    if (resultSet.next()) {
                        String temp = resultSet.getString("account_type");
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
                        usernameField.setText("");
                        passwordField.setText("");
                    }
                }


            } catch (Exception exception)
            {
                exception.printStackTrace();
            }

        }

        if (e.getSource() == registerButton) {

            try
            {
                String data = null;
                try {
                    File myObj = new File("src/main/resources/com/project/sportsleaguemanagementproject/data.txt");
                    Scanner myReader = new Scanner(myObj);
                    while (myReader.hasNextLine()) {
                        data = myReader.nextLine();
                    }
                    myReader.close();
                } catch (FileNotFoundException e2) {
                    System.out.println("An error occurred.");
                    e2.printStackTrace();
                }

                String username = usernameField.getText();
                String password = passwordField.getText();
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sportsleaguemanagement", "root", data);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from account where username='" + username + "'");
                if (resultSet.next())
                {
                    System.out.println("USERNAME ALREADY EXISTS");
                    messageDisplay.setText("Username already exists");
                    usernameField.setText("");
                    passwordField.setText("");
                }

                else if(username.equals(""))
                {
                    messageDisplay.setText("username field is empty");
                }
                else if(password.equals(""))
                {
                    messageDisplay.setText("password field is empty");
                }
                else
                {
                    String sql = "INSERT INTO account (username,password) VALUES ('" + username + "' , '" + password + "')";
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