package susmanager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Settings implements Initializable {

  @FXML
  private ImageView trollFace;

  @FXML
  private ChoiceBox selectTheme;

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
  private void switchToSelectServerType() throws IOException {
    App.setRoot("select_server_type");
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

  @FXML
  private void color_switchToCafe() {
    App.loadTheme("cafe");
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    System.out.println("aaaaa");
    ChoiceBox selectTheme = new ChoiceBox();
    selectTheme.getItems().add("Choice 1");
    selectTheme.getItems().add("Choice 2");
    selectTheme.getItems().add("Choice 3");
  }
}