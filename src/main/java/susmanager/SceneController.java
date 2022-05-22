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
  void addPassword() throws IOException {
    pass.add(new Entry(email.getText(), email.getText(), password.getText(), 0));
    switchToMainScreen();
  }
}
