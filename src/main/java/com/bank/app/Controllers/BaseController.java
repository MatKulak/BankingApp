package com.bank.app.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public abstract class BaseController implements Initializable {

    //return path to .fxml file
    protected abstract String getFXMLPath();

    //return path to .css file
    protected abstract String getCSSPath();

    //return AnchorPane used to switch scenes
    protected abstract AnchorPane getContentAnchorPane();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        switchScene(getFXMLPath());
    }

    //switch AnchorPane content to provided .fxml file
    //sets .css file to scene
    @FXML
    protected void switchScene(String fxml) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent newScene = null;
        try {
            newScene = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getContentAnchorPane().getChildren().setAll(newScene);

        String css = Objects.requireNonNull(this.getClass().getResource(getCSSPath())).toExternalForm();
        assert newScene != null;
        newScene.getStylesheets().add(css);
    }

    @FXML
    protected void onLogout(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bank/app/Fxml/Login/login.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(root);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);

        currentStage.setTitle("Login");
        currentStage.setResizable(false);

        currentStage.show();
    }
}

