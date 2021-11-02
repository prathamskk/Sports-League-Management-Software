package com.project.sportsleaguemanagementproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class MainApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage loginScreen) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ui/LoginScreen.fxml")));
        Scene loginPage = new Scene(root);
        loginPage.getStylesheets().add(Objects.requireNonNull(getClass().getResource("ui/stylesheets/LoginScreenStyleSheet.css")).toExternalForm());
        loginScreen.setTitle("Sports League Manager");
        loginScreen.setScene(loginPage);
        loginScreen.setResizable(false);
        loginScreen.show();
    }
}