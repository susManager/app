package susmanager;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class select_server_type {

    @FXML
    private TextField server_address, no_address_given;

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
      App.setRoot("login_local");
    }

    @FXML
    private void serverRemote() throws IOException {
      no_address_given.setOpacity(0);
      if(server_address.getText().length() < 1){
      no_address_given.setOpacity(1);
     }
     else{App.setRoot("login_remote");
    }
    }
}
