module susmanager {
    requires javafx.controls;
    requires javafx.fxml;

    opens susmanager to javafx.fxml;
    exports susmanager;
}
