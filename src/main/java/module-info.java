module susmanager {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.media;
  requires javafx.graphics;

  opens susmanager to javafx.fxml;
  exports susmanager ;
}
