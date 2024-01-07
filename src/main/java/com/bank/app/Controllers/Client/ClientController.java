package com.bank.app.Controllers.Client;

import com.bank.app.Controllers.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController extends BaseController implements Initializable {

    @FXML
    private AnchorPane contentAnchorPane;

    //set default AnchorPane content
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        switchScene("/com/bank/app/Fxml/Client/dashboardPage.fxml");
    }

    @Override
    protected String getFXMLPath() {
        return "/com/bank/app/Fxml/Admin/addClientPage.fxml";
    }

    @Override
    protected String getCSSPath() {
        return "/com/bank/app/Style/css/client.css";
    }

    @Override
    protected AnchorPane getContentAnchorPane() {
        return contentAnchorPane;
    }

    //switch AnchorPane content to provided .fxml
    @FXML
    void onDashboard() {
        switchScene("/com/bank/app/Fxml/Client/dashboardPage.fxml");
    }

    @FXML
    void onProfile() {
        switchScene("/com/bank/app/Fxml/Client/profilePage.fxml");
    }

    @FXML
    void onTransactions() {
        switchScene("/com/bank/app/Fxml/Client/transactionsPage.fxml");
    }
}

