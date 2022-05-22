module susmanager {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.media;
  requires javafx.graphics;
  requires susManager.lib;

  opens susmanager to javafx.fxml;
  exports susmanager ;
}
