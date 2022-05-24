package susmanager;

import fundur.systems.lib.Entry;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddPassword {

  @FXML
  private TextField name, user, password, notes;

  @FXML
  private void playThudSound() {
    App.playThudSound();
  }

  @FXML
  private void switchToSettings() throws IOException {
    App.setRoot("settings");
  }

  @FXML
  private void switchToMainScreen() throws IOException {
    App.setRoot("main_screen");
  }

  @FXML
  private void addPassword() throws IOException {
    MainScreen.addPassword(
      new Entry(
        name.getText(),
        user.getText(),
        password.getText(),
        notes.getText(),
        System.currentTimeMillis() / 1000
      )
    );
    switchToMainScreen();
  }
}
