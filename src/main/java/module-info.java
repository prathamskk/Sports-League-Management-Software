module com.project.sportsleaguemanagementproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.project.sportsleaguemanagementproject to javafx.fxml;
    exports com.project.sportsleaguemanagementproject;
    exports com.project.sportsleaguemanagementproject.ui;
    opens com.project.sportsleaguemanagementproject.ui to javafx.fxml;
    opens com.project.sportsleaguemanagementproject.model;
}