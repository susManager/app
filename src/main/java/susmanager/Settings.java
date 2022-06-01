package susmanager;

import javafx.beans.value.ObservableValue;
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
    selectTheme.getItems().add("Default");
    selectTheme.getItems().add("Nord");
    selectTheme.getItems().add("Tokyo Night");
    selectTheme.getItems().add("Cafe");
    selectTheme.setOnAction(event -> {switchThemePre();});
  }

  @FXML
  private void switchThemePre() {
    switch (selectTheme.getValue().toString()){
      case "Default":
        color_switchToDefault();
        break;
      case "Nord":
        color_switchToNord();
        break;
      case "Tokyo Night":
        color_switchToTokyoNight();
        break;
      case "Cafe":
        color_switchToCafe();
        break;
    }
  }
}