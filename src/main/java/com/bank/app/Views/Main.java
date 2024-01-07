package com.bank.app.Views;

import com.bank.app.Classes.DataBaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    //load initial login stage
    @Override
    public void start(Stage primaryStage) {
        //load database properties
        DataBaseHandler.loadProperties();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bank/app/Fxml/Login/login.fxml"));

        Parent root;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Banking App");
            primaryStage.setResizable(false);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {launch();}
}

