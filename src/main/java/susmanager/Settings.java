package susmanager;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class Settings {

  @FXML
  private ImageView trollFace;

  @FXML
  private Pane settings_background;

  @FXML
  private Pane title_banner;

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
     System.out.println("switched to default color theme!");
    settings_background.setStyle("-fx-background-color: #050714");
    title_banner.setStyle("-fx-background-color: #1f1543");
  }

  @FXML
  private void color_switchToNord() {
    System.out.println("switched to Nord color theme!");
    App.loadTheme("nord");
    /*settings_background.setStyle("-fx-background-color: #617482");
    title_banner.setStyle("-fx-background-color: #88c0d0");

     */
  }

}