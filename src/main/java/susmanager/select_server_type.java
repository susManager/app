package susmanager;

import java.io.IOException;
import javafx.fxml.FXML;

public class select_server_type {

    @FXML
    private void playThudSound() {
      App.playThudSound();
    }

    @FXML
    private void switchToSettingsLogin() throws IOException {
      App.setRoot("settings");
    }

    @FXML
    private void serverLocal() throws IOException {
      App.setRoot("login");
    }

    @FXML
    private void serverRemote() throws IOException {
      App.setRoot("login");
    }
    
}
