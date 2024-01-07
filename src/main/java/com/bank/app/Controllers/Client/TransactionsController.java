package com.bank.app.Controllers.Client;

import com.bank.app.Classes.Client;
import com.bank.app.Classes.ClientDAO;
import com.bank.app.Classes.Transaction;
import com.bank.app.Classes.TransactionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TransactionsController implements Initializable {
    private Client client;

    @FXML
    private TableColumn<Transaction, String> dateTableColumn;

    @FXML
    private TableColumn<Transaction, String>  messageTableColumn;

    @FXML
    private TableColumn<Transaction, String>  receiverTableColumn;

    @FXML
    private TableColumn<Transaction, String> senderTableColumn;

    @FXML
    private TableView<Transaction> transactionsTableView;

    @FXML
    private TableColumn<Transaction, String>  transferTableColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        client = ClientDAO.getClient(Client.getInitialLogin());
        assert client != null;

        //populate table view
        List<Transaction> transactionList = TransactionDAO.retrieveAllTransactions(client.getLogin());
        ObservableList<Transaction> transactionObservableList = FXCollections.observableArrayList(transactionList);
        transactionsTableView.setItems(transactionObservableList);

        //set up cell value factories for each column
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        senderTableColumn.setCellValueFactory(new PropertyValueFactory<>("senderLogin"));
        receiverTableColumn.setCellValueFactory(new PropertyValueFactory<>("receiverLogin"));
        transferTableColumn.setCellValueFactory(new PropertyValueFactory<>("transferAmount"));
        messageTableColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
    }
}
