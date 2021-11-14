package com.project.sportsleaguemanagementproject.singleton;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneSwitcher {
    private static SceneSwitcher single_instance = null;
    private SceneSwitcher() {
    }
    public static void switchTo(Class class_, ActionEvent event,String FXMLFilePath) throws IOException {
        if (single_instance == null){
            single_instance = new SceneSwitcher();
        }
        Parent root = FXMLLoader.load(Objects.requireNonNull(class_.getResource(FXMLFilePath)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
