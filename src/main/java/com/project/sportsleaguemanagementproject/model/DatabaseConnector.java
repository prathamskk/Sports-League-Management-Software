package com.project.sportsleaguemanagementproject.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DatabaseConnector {

    public static Connection getConnection() throws SQLException{

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

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sportsleaguemanagement", "root", data);
        return connection;
    }
}
