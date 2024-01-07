package com.bank.app.Controllers.Admin;

import com.bank.app.Classes.Client;
import com.bank.app.Classes.ClientDAO;
import com.bank.app.Classes.Transaction;
import com.bank.app.Classes.TransactionDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddClientController implements Initializable {

    @FXML
    private CheckBox depositMoneyCheckBox;

    @FXML
    private TextField depositMoneyTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private Label generatedLoginLabel;

    @FXML
    private Label successLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField lastNameTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //used to set CheckBox logic
        depositMoneyTextField.setDisable(true);
        depositMoneyCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            depositMoneyTextField.setDisable(!newValue);
            if (!newValue) {
                depositMoneyTextField.clear();
            }
        });
    }

    @FXML
    void onAddClientButton() {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String password = passwordTextField.getText();
        String initialStringBalance = depositMoneyTextField.getText();
        double initialDoubleBalance = 0.0;

        //input validation
        if (firstName.isEmpty() || lastName.isEmpty() || password.isEmpty()) {
            errorLabel.setText("No field can be empty.");
            return;
        }

        if (depositMoneyCheckBox.isSelected() && initialStringBalance.isEmpty()) {
            errorLabel.setText("If you select initial balance, you must provide it.");
            return;
        }

        if (!firstName.matches("[a-zA-Z]+") || !lastName.matches("[a-zA-Z]+")) {
            errorLabel.setText("Both first and last name can only consist of letters.");
            return;
        }

        if (depositMoneyCheckBox.isSelected()) {
            try {
                initialDoubleBalance = Double.parseDouble(initialStringBalance);
            } catch (NumberFormatException e) {
                errorLabel.setText("Initial balance must be of type double.");
                return;
            }
        }
        //add client
        Client client = new Client(firstName, lastName, password);
        client.setAccountBalance(initialDoubleBalance);
        ClientDAO.addClient(client);

        //add transaction
        Transaction transaction = new Transaction(client.getLogin(), client.getLogin(), client.getLogin(), LocalDate.now().toString(), initialDoubleBalance, "Initial balance");
        TransactionDAO.addTransaction(transaction);

        generatedLoginLabel.setText(client.getLogin());
        successLabel.setText("Client Added Successfully!");
        firstNameTextField.clear();
        lastNameTextField.clear();
        passwordTextField.clear();
        depositMoneyTextField.clear();
        errorLabel.setText("");
    }
}
