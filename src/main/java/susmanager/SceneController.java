package susmanager;

import java.io.IOException;
import javafx.fxml.FXML;

public class SceneController {

  @FXML
  private void switchToSettings() throws IOException {
    App.setRoot("settings");
  }

  @FXML
  private void switchToMainScreen() throws IOException {
    App.setRoot("main_screen");
  }

  @FXML
  private void switchToLogin() throws IOException {
    App.setRoot("login");
  }

  @FXML
  private void switchToSettingsLogin() throws IOException {
    App.setRoot("settings_login");
  }

  @FXML
  private void playThudSound() throws IOException {
    App.playThudSound();
  }
}
