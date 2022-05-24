package susmanager;

import java.io.IOException;
import javafx.fxml.FXML;

public class Settings {

  @FXML
  private void playThudSound() {
    App.playThudSound();
  }

  @FXML
  private void playBackgroundMusic() {
    App.toggleBackgroundMusic();
  }

  @FXML
  private void switchBack() throws IOException {
    App.popBack();
  }

  @FXML
  private void switchToLogin() throws IOException {
    App.setRoot("login");
  }
}
