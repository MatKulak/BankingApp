package com.bank.app.Controllers.Admin;

import com.bank.app.Controllers.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController extends BaseController implements Initializable {

    @FXML
    private AnchorPane contentAnchorPane;

    //sets default scene to addClientPage
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        switchScene("/com/bank/app/Fxml/Admin/addClientPage.fxml");
    }

    @Override
    protected String getFXMLPath() {
        return "/com/bank/app/Fxml/Admin/addClientPage.fxml";
    }

    @Override
    protected String getCSSPath() {
        return "/com/bank/app/Style/css/admin.css";
    }

    @Override
    protected AnchorPane getContentAnchorPane() {
        return contentAnchorPane;
    }

    //switch AnchorPane content to provided .fxml
    @FXML
    void onAddClient() {
        switchScene("/com/bank/app/Fxml/Admin/addClientPage.fxml");
    }

    @FXML
    void onDeposit() {
        switchScene("/com/bank/app/Fxml/Admin/depositPage.fxml");
    }

    @FXML
    void onViewClients() {
        switchScene("/com/bank/app/Fxml/Admin/showClientsPage.fxml");
    }
}



