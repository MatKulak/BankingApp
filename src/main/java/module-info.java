module com.bank.bankingapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.bank.app.Views to javafx.fxml, javafx.graphics;
    opens com.bank.app.Controllers.Admin to javafx.fxml, javafx.graphics;
    opens com.bank.app.Classes to javafx.base, javafx.fxml;
    opens com.bank.app.Controllers.Client;

    exports com.bank.app.Views;
    exports com.bank.app.Controllers;
    exports com.bank.app.Controllers.Admin;
    exports com.bank.app.Classes;
    exports com.bank.app.Controllers.Client;
    opens com.bank.app.Controllers to javafx.base, javafx.fxml, javafx.graphics;
    exports com.bank.app.Controllers.Login;
    opens com.bank.app.Controllers.Login to javafx.base, javafx.fxml, javafx.graphics;
}