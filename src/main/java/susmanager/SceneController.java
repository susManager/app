package susmanager;

import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;

public class SceneController extends Control {

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
  private void switchToAddPassword() throws IOException {
    App.setRoot("add_password");
  }

  @FXML
  private void switchToSettingsAddPassword() throws IOException {
    App.setRoot("settings_add_password");
  }

  @FXML
  private void playThudSound() throws IOException {
    App.playThudSound();
  }

  @FXML
  private ListView listView1;

  @FXML
  private void populateList(){
    listView1.getItems().addAll("Eins", "Zwei", "Drei", "Vier");
  }

  @FXML
  private void searchList(){
    App.searchList();
  }
}
