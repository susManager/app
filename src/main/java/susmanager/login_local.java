package susmanager;

import fundur.systems.lib.Entry;
import fundur.systems.lib.FileManager;
import fundur.systems.lib.Manager;
import fundur.systems.lib.sec.EncrState;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import javax.crypto.BadPaddingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static susmanager.App.logErr;

public class login_local {

  @FXML
  private TextField errorMsg, login_pwd;

  /**
   * Plays the vine boom effect when the logo is clicked
   */
  @FXML
  private void playThudSound() {
    App.playThudSound();
  }

  /**
   * switches to the settings page
   * @throws IOException if xml not found
   */
  @FXML
  private void switchToSettingsLogin() throws IOException {
    App.setRoot("settings");
  }

  @FXML
  private void switchToLoginRemote() throws IOException {
    App.setRoot("login_remote");
  }

  @FXML
  private void onSelectConfig() {
    FileChooser fc = new FileChooser();
    fc.setTitle("Choose your config");
    File f = fc.showOpenDialog(login_pwd.getScene().getWindow());
    errorMsg.setOpacity(0);
    EncrState state;

    try {
      state = FileManager.getEncrStateFromFile(f.getPath());
      App.getState().setEncrstate(state);
    } catch (FileNotFoundException e) {
      errorMsg.setOpacity(1);
      errorMsg.setText("config file not found");
      logErr("config file not found");
    }
  }

  @FXML
  private void switchToMainScreen() throws IOException {
    MainScreen.setupDefaultPasswords();
    App.setRoot("main_screen");
  }

  @FXML
  void onSelectEncrypted() {
    FileChooser fc = new FileChooser();
    fc.setTitle("Choose the encrypted file");
    File f = fc.showOpenDialog(login_pwd.getScene().getWindow());
    App.getState().setEncrypted(f);
    errorMsg.setOpacity(0);
    if (!f.exists()) {
      errorMsg.setOpacity(1);
      errorMsg.setText("Encrypted file not found!");
    }
  }
}
