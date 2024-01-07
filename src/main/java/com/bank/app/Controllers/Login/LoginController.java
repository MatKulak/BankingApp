package com.bank.app.Controllers.Login;

import com.bank.app.Classes.Client;
import com.bank.app.Classes.ClientDAO;
import com.bank.app.Classes.DataBaseHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private ChoiceBox<String> accountChoiceBox;

    @FXML
    private TextField loginTextField;

    @FXML
    private Label messageLabel;

    @FXML
    private PasswordField passwordPasswordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //add options to ChoiceBox
        String[] accountType = {"Admin", "Client"};
        accountChoiceBox.getItems().addAll(accountType);
    }

    @FXML
    void onLogin() {
        String accType = accountChoiceBox.getValue();
        String login = loginTextField.getText();
        String password = passwordPasswordField.getText();

        //check database connection
        if (DataBaseHandler.getConnection() == null) {
            messageLabel.setText("Can not connect to database");
            return;
        }

        //input validation
        if (accType == null) {
            messageLabel.setText("Select account type.");
            return;
        }

        if (login.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Fields can not be empty.");
            return;
        }

        //client validation
        if (accType.equals("Client")) {

            //check if there are clients registered
            if (ClientDAO.isClientsDatabaseEmpty()) {
                messageLabel.setText("There are currently no clients registered.");
                return;
            }

            if (!ClientDAO.validateClientLogin(login,password)) {
                messageLabel.setText("Invalid login credentials for client.");
                return;
            }
            Client.setInitialLogin(login);
            switchStage("/com/bank/app/Fxml/Client/client.fxml");
        }

        //admin validation
        if (accType.equals("Admin")) {
            if (!validateAdminLogin(login,password)) {
                messageLabel.setText("Invalid login credentials for admin!");
                return;
            }
            switchStage("/com/bank/app/Fxml/Admin/admin.fxml");
        }
    }

    // validates admin account
    // default login:       admin
    // default password:    password
    private boolean validateAdminLogin(String login, String password) {
        return login.equals("admin") && password.equals("password");
    }

    // switch stage for provided fxml file
    private void switchStage(String fxml){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert root != null;
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Banking App");

        Stage currentStage = (Stage) accountChoiceBox.getScene().getWindow();
        currentStage.close();

        stage.show();
    }
}

