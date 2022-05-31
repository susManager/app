package susmanager;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import java.io.IOException;


public class Settings {

  @FXML
  private ImageView trollFace;

  @FXML
  private void playThudSound() {
    App.playThudSound();
  }

  @FXML
  private void playBackgroundMusic() {
    App.toggleBackgroundMusic();
  }

  @FXML
  private void playAllahMode() {
    App.playAllahMode();
  }

  @FXML
  private void playNotification() {
    App.playNotification();
  }

  @FXML
  private void switchBack() throws IOException {
    App.popBack();
  }

  @FXML
  private void turnOff() {
    trollFace.setOpacity(1);
  }

  @FXML
  private void switchToLogin() throws IOException {
    App.setRoot("login");
  }

  @FXML
  private void color_switchToDefault() {
    App.loadTheme("default");
  }

  @FXML
  private void color_switchToNord() {
    App.loadTheme("nord");
  }

  @FXML
  private void color_switchToTokyoNight() {
    App.loadTheme("tokyo_night");
  }

}