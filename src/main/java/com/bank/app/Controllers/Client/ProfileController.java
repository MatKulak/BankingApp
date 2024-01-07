package com.bank.app.Controllers.Client;

import com.bank.app.Classes.Client;
import com.bank.app.Classes.ClientDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    private Client client;

    @FXML
    private PasswordField newPasswordTextField;

    @FXML
    private PasswordField oldPasswordTextField;

    @FXML
    private Label passwordMessageLabel;

    @FXML
    private Label passwordSuccessLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        client = ClientDAO.getClient(Client.getInitialLogin());
    }

    @FXML
    void onChangePasswordButton() {
        String oldPassword = oldPasswordTextField.getText();
        String newPassword = newPasswordTextField.getText();

        //input validation
        if (oldPassword.isEmpty() || newPassword.isEmpty()) {
            passwordMessageLabel.setText("Password fields can not be empty.");
            return;
        }

        if (!oldPassword.equals(client.getPassword())) {
            passwordMessageLabel.setText("Please enter correct current password.");
            return;
        }

        if (oldPassword.equals(newPassword)) {
            passwordMessageLabel.setText("New password can not be the same.");
            return;
        }

        //change password logic
        Client newClient = ClientDAO.getClient(client.getLogin());
        assert newClient != null;

        newClient.setPassword(newPassword);
        ClientDAO.replaceClient(client.getLogin(), newClient);

        passwordSuccessLabel.setText("Password changed successfully!");
        passwordMessageLabel.setText("");
        oldPasswordTextField.clear();
        newPasswordTextField.clear();
    }
}
