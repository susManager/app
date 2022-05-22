package susmanager;

import javafx.fxml.FXML;

import java.io.IOException;

public class Settings {

    @FXML
    private void playThudSound() {
        App.playThudSound();
    }

    @FXML
    private void switchBack() throws IOException {
        App.popBack();
    }

    @FXML
    private void switchToLogin() throws IOException {
        SceneController.setupPasswords();
        App.setRoot("login");
    }
}
