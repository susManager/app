package susmanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

import fundur.systems.lib.Entry;

public class SceneController {

  @FXML
  public TextField email;

  public TextField password;
  public TextField notes;

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
    setupPasswords();
    App.setRoot("login");
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
  private ListView<Entry> listView1 = new ListView<>();

  @FXML
  private ImageView nothing_found;

  @FXML
  private TextField searchBar1;

  public boolean allowPopulation = true;


  public static void setupPasswords() {
    pass = new ArrayList<>(Arrays.asList(
            new Entry("Steam", "steamUser", "steamPassword", 1),
            new Entry("Discord", "discordUser", "discordPassword", 1),
            new Entry("Teams", "teamsUser", "teamsPassword", 1),
            new Entry("Google", "googleUser", "googlePassword", 1)
    ));
  }

  private static ArrayList<Entry> pass;

  @FXML
  private void populateList() {
    if (allowPopulation) {
      listView1.getItems().addAll(pass);
      allowPopulation = false;
    }
  }

  @FXML
  void search(ActionEvent event) {
    listView1.getItems().clear();
    listView1.getItems().addAll(searchList(searchBar1.getText(), pass));
  }

  @FXML
  void search2(KeyEvent event) {
    listView1.getItems().clear();
    listView1.getItems().addAll(searchList(searchBar1.getText(), pass));
  }

  @FXML
  void addPassword() throws IOException {
    pass.add(new Entry(email.getText(), email.getText(), password.getText(), 0));
    switchToMainScreen();
  }

  private List<Entry> searchList(
    String searchWords,
    List<Entry> results
  ) {
    nothing_found.setOpacity(0);

    List<String> searchWordsArray = Arrays.asList(
      searchWords.trim().split(" ")
    );

    results =
      results
        .stream()
        .filter(
          input -> {
            return searchWordsArray
              .stream()
              .allMatch(
                word -> input.name().toLowerCase().contains(word.toLowerCase())
              );
          }
        )
        .collect(Collectors.toList());

    if (results.size() == 0) {
      nothing_found.setOpacity(1);
    }
    return results;
  }
}
