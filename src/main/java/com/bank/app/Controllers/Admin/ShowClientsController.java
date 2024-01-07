package com.bank.app.Controllers.Admin;

import com.bank.app.Classes.Client;
import com.bank.app.Classes.ClientDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShowClientsController implements Initializable {

    @FXML
    private TableView<Client> clientTableView;

    @FXML
    private TableColumn<Client, String> balanceTableColumn;

    @FXML
    private TableColumn<Client, String> firstNameTableColumn;

    @FXML
    private TableColumn<Client, String> lastNameTableColumn;

    @FXML
    private TableColumn<Client, String> loginTableColumn;

    @FXML
    private TableColumn<Client, String> passwordTableColumn;

    @FXML
    private TableColumn<Client, String> registrationTableColumn;

    @FXML
    private TextField deleteTextField;

    @FXML
    private Label messageLabel;

    @FXML
    private TextField searchTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //populate table view
        List<Client> clientList = ClientDAO.retrieveAllClients();
        ObservableList<Client> clientObservableList = FXCollections.observableArrayList(clientList);
        clientTableView.setItems(clientObservableList);

        // set up cell value factories for each column
        balanceTableColumn.setCellValueFactory(new PropertyValueFactory<>("accountBalance"));
        firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        loginTableColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        passwordTableColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        registrationTableColumn.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
    }

    @FXML
    void onDeleteButton() {
        String login = deleteTextField.getText();

        //input validation
        if (login.isEmpty()) {
            messageLabel.setText("Account login can not be empty.");
            return;
        }

        if (!ClientDAO.doesClientExist(login)) {
            messageLabel.setText("Account does not exist.");
            return;
        }

        Client client = ClientDAO.getClient(login);
        assert client != null;

        //delete client
        ClientDAO.deleteClient(client);
        clientTableView.getItems().remove(client);

        messageLabel.setText("Account deleted successfully!");
        clientTableView.refresh();
        deleteTextField.clear();
    }

    //displays clients if their login match provided substring
    @FXML
    void onSearchButton() {
        String searchString = searchTextField.getText().toLowerCase();

        if (searchString.isEmpty()) {
            clientTableView.getItems().clear();
            clientTableView.getItems().addAll(ClientDAO.retrieveAllClients());
        } else {
            List<Client> filteredClients = ClientDAO.retrieveAllClients().stream()
                    .filter(client -> client.getLogin().toLowerCase().contains(searchString))
                    .toList();

            clientTableView.getItems().clear();
            clientTableView.getItems().addAll(filteredClients);
        }
    }
}
