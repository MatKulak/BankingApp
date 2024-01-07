package com.bank.app.Controllers.Admin;

import com.bank.app.Classes.Client;
import com.bank.app.Classes.ClientDAO;
import com.bank.app.Classes.Transaction;
import com.bank.app.Classes.TransactionDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.time.LocalDate;

public class DepositController {

    @FXML
    private TextField clientDepositTextField;

    @FXML
    private TextField clientWithdrawTextField;

    @FXML
    private TextField depositAmountTextField;

    @FXML
    private Label depositMessageLabel;

    @FXML
    private TextField payeeTransferTextField;

    @FXML
    private TextField senderTransferTextField;

    @FXML
    private TextField transferAmountTextField;

    @FXML
    private Label transferMessageLabel;

    @FXML
    private TextField withdrawAmountTextField;

    @FXML
    private Label withdrawMessageLabel;

    @FXML
    private Label depositSuccessLabel;

    @FXML
    private Label withdrawSuccessLabel;

    @FXML
    private Label transferSuccessLabel;

    @FXML
    void onDepositButton() {
        String login = clientDepositTextField.getText();
        String stringDeposit = depositAmountTextField.getText();
        double deposit;

        //input validation
        if (clientDepositTextField.getText().isEmpty() || depositAmountTextField.getText().isEmpty()) {
            depositMessageLabel.setText("Fields can not be empty.");
            return;
        }

        if (!ClientDAO.doesClientExist(login)) {
            depositMessageLabel.setText("Client does not exist.");
            return;
        }

        try {
            deposit = Double.parseDouble(stringDeposit);
        } catch (NumberFormatException e) {
            depositMessageLabel.setText("Deposit amount must be double.");
            return;
        }

        if (deposit < 0) {
            depositMessageLabel.setText("Deposit must be positive.");
            return;
        }

        //update client account balance
        Client client = ClientDAO.getClient(login);
        assert client != null;
        client.deposit(deposit);
        ClientDAO.replaceClient(login, client);

        //add transaction
        Transaction transaction = new Transaction(login, login, login, LocalDate.now().toString(), deposit, "Deposit.");
        TransactionDAO.addTransaction(transaction);

        depositMessageLabel.setText("");
        depositSuccessLabel.setText("Deposit was placed successfully!");
        clientDepositTextField.clear();
        depositAmountTextField.clear();
    }

    @FXML
    void onWithdrawButton() {
        String login = clientWithdrawTextField.getText();
        String stringWithdraw = withdrawAmountTextField.getText();
        double withdraw;

        //input validation
        if(clientWithdrawTextField.getText().isEmpty() || withdrawAmountTextField.getText().isEmpty()) {
            withdrawMessageLabel.setText("Fields can not be empty.");
            return;
        }

        if(!ClientDAO.doesClientExist(login)) {
            withdrawMessageLabel.setText("Client does not exist.");
            return;
        }

        try {
            withdraw = Double.parseDouble(stringWithdraw);
        } catch (NumberFormatException e) {
            withdrawMessageLabel.setText("Withdraw amount must be double.");
            return;
        }

        if (withdraw < 0) {
            withdrawMessageLabel.setText("Withdraw must be positive.");
            return;
        }

        Client client = ClientDAO.getClient(login);
        assert client != null;

        if (client.getAccountBalance() < withdraw) {
            withdrawMessageLabel.setText("Insufficient funds");
            return;
        }

        //update client account balance
        client.withdraw(withdraw);
        ClientDAO.replaceClient(login, client);

        //add transaction
        Transaction transaction = new Transaction(login,login,login, LocalDate.now().toString(),(-1) * withdraw,"Withdraw.");
        TransactionDAO.addTransaction(transaction);

        withdrawMessageLabel.setText("");
        withdrawSuccessLabel.setText("Money was withdrawn successfully!");
        withdrawAmountTextField.clear();
        withdrawAmountTextField.clear();
    }

    @FXML
    void onTransferButton() {
        String senderLogin = senderTransferTextField.getText();
        String payeeLogin = payeeTransferTextField.getText();
        String stringTransfer = transferAmountTextField.getText();
        double transfer;

        //input validation
        if(senderTransferTextField.getText().isEmpty() || payeeTransferTextField.getText().isEmpty() || transferAmountTextField.getText().isEmpty()) {
            transferMessageLabel.setText("Fields can not be empty.");
            return;
        }

        if(!ClientDAO.doesClientExist(senderLogin)) {
            transferMessageLabel.setText("Sender does not exist.");
            return;
        }

        if(!ClientDAO.doesClientExist(payeeLogin)) {
            transferMessageLabel.setText("Payee does not exist.");
            return;
        }

        try {
            transfer = Double.parseDouble(stringTransfer);
        } catch (NumberFormatException e) {
            transferMessageLabel.setText("Transfer amount must be double.");
            return;
        }

        if (transfer < 0) {
            transferMessageLabel.setText("Transfer must be positive.");
            return;
        }

        Client senderClient = ClientDAO.getClient(senderLogin);
        assert senderClient != null;

        if (senderClient.getAccountBalance() < transfer) {
            transferMessageLabel.setText("Insufficient funds.");
            return;
        }

        Client payeeClient = ClientDAO.getClient(payeeLogin);
        assert payeeClient != null;

        //update sender and payee account balance
        senderClient.withdraw(transfer);
        payeeClient.deposit(transfer);
        ClientDAO.replaceClient(senderLogin, senderClient);
        ClientDAO.replaceClient(payeeLogin, payeeClient);

        //add transactions
        Transaction senderTransaction = new Transaction(senderLogin, senderLogin, payeeLogin, LocalDate.now().toString(),(-1) * transfer,"Transfer to: " + payeeLogin);
        TransactionDAO.addTransaction(senderTransaction);
        Transaction payeeTransaction = new Transaction(payeeLogin, senderLogin, payeeLogin, LocalDate.now().toString(),transfer,"Transfer from: " + senderLogin);
        TransactionDAO.addTransaction(payeeTransaction);

        transferMessageLabel.setText("");
        transferSuccessLabel.setText("Transfer was placed successfully!");
        transferAmountTextField.clear();
        senderTransferTextField.clear();
        payeeTransferTextField.clear();
    }
}

