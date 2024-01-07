package com.bank.app.Controllers.Client;

import com.bank.app.Classes.Client;
import com.bank.app.Classes.ClientDAO;
import com.bank.app.Classes.Transaction;
import com.bank.app.Classes.TransactionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    private Client client;

    @FXML
    private TextArea messageTextArea;

    @FXML
    private Label accountBalanceLabel;

    @FXML
    private TextField amountTextField;

    @FXML
    private Label dateLabel;

    @FXML
    private TableColumn<Transaction, String> dateTableColumn;

    @FXML
    private Label expensesLabel;

    @FXML
    private Label hiLabel;

    @FXML
    private Label incomeLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField payeeAddressTextField;

    @FXML
    private TableColumn<Transaction, String> receiverTableColumn;

    @FXML
    private TableColumn<Transaction, String> senderTableColumn;

    @FXML
    private TableView<Transaction> transactionsTableView;

    @FXML
    private TableColumn<Transaction, String> transferTableColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        client = ClientDAO.getClient(Client.getInitialLogin());

        //set welcome label
        assert client != null;
        hiLabel.setText("Hi, " + client.getFirstName() + "!");

        //set date label
        dateLabel.setText(LocalDate.now().toString());

        //show balance
        accountBalanceLabel.setText("$" + client.getAccountBalance());

        //show income
        double income;
        income = TransactionDAO.getIncomeSum(client.getLogin());
        incomeLabel.setText("$" + income);

        //show expenses
        double expenses;
        expenses = TransactionDAO.getExpensesSum(client.getLogin());
        expensesLabel.setText("$" + expenses);

        //show client name
        nameLabel.setText(client.getFirstName() + " " + client.getLastName());

        //populate transactions table
        List<Transaction> transactionList = TransactionDAO.retrieveAllTransactions(client.getLogin());
        ObservableList<Transaction> transactionObservableList = FXCollections.observableArrayList(transactionList);
        transactionsTableView.setItems(transactionObservableList);

        //set up cell value factories for each column
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        senderTableColumn.setCellValueFactory(new PropertyValueFactory<>("senderLogin"));
        receiverTableColumn.setCellValueFactory(new PropertyValueFactory<>("receiverLogin"));
        transferTableColumn.setCellValueFactory(new PropertyValueFactory<>("transferAmount"));
    }

    @FXML
    void onSendMoneyButton() {
        String senderLogin = client.getLogin();
        String payeeLogin = payeeAddressTextField.getText();
        String stringTransfer = amountTextField.getText();
        double transfer;

        //input validation
        if (payeeAddressTextField.getText().isEmpty() || amountTextField.getText().isEmpty()) {
            messageLabel.setText("Fields can not be empty.");
            return;
        }

        if (!ClientDAO.doesClientExist(payeeLogin)) {
            messageLabel.setText("Payee does not exist!");
            return;
        }

        try {
            transfer = Double.parseDouble(stringTransfer);
        } catch (NumberFormatException e) {
            messageLabel.setText("Transfer amount must be double!");
            return;
        }

        Client sender = ClientDAO.getClient(senderLogin);
        assert sender != null;

        if (sender.getAccountBalance() < transfer) {
            messageLabel.setText("Insufficient funds");
            return;
        }

        Client payee = ClientDAO.getClient(payeeLogin);
        assert payee != null;

        //transfer funds
        sender.withdraw(transfer);
        payee.deposit(transfer);
        ClientDAO.replaceClient(senderLogin, sender);
        ClientDAO.replaceClient(payeeLogin, payee);

        //register transactions
        Transaction senderTransaction = new Transaction(senderLogin, senderLogin, payeeLogin, LocalDate.now().toString(), (-1) * transfer, "transfer to: " + payeeLogin);
        TransactionDAO.addTransaction(senderTransaction);
        Transaction payeeTransaction = new Transaction(payeeLogin, senderLogin, payeeLogin, LocalDate.now().toString(), transfer, messageTextArea.getText());
        TransactionDAO.addTransaction(payeeTransaction);

        messageLabel.setText("");
        messageLabel.setText("Success!");
        payeeAddressTextField.clear();
        messageTextArea.clear();
        amountTextField.clear();
    }
}
