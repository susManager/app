module susmanager {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.media;

  opens susmanager to javafx.fxml;
  exports susmanager ;
}
