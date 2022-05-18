package susmanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class SceneController {

  //switch to different Scenes
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

  //do le funny sound
  @FXML
  private void playThudSound() throws IOException {
    App.playThudSound();
  }

  //Code for the whole ListView things, includes population and searching
  @FXML
  private ListView<String> listView1;

  @FXML
  private TextField searchBar1;

  public boolean allowPopulation = true;

  ArrayList<String> words = new ArrayList<>(
    Arrays.asList("Steam", "Discord", "Teams", "Google")
  );

  @FXML
  private void populateList() {
    if (allowPopulation == true) {
      listView1.getItems().addAll(words);
    }
    allowPopulation = false;
  }

  @FXML
  void search(ActionEvent event) {
    listView1.getItems().clear();
    listView1.getItems().addAll(searchList(searchBar1.getText(), words));
  }

  private List<String> searchList(
    String searchWords,
    List<String> listOfStrings
  ) {
    List<String> searchWordsArray = Arrays.asList(
      searchWords.trim().split(" ")
    );

    return listOfStrings
      .stream()
      .filter(
        input -> {
          return searchWordsArray
            .stream()
            .allMatch(word -> input.toLowerCase().contains(word.toLowerCase()));
        }
      )
      .collect(Collectors.toList());
  }
}
