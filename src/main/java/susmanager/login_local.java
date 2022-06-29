package susmanager;

import fundur.systems.lib.FileManager;
import fundur.systems.lib.sec.EncrState;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

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
  private void defaultMainScreen() throws IOException {
    MainScreen.setupDefaultPasswords();
    App.getState().setLogged(true);
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

  @FXML
  private void localLogin() {
    errorMsg.setOpacity(0);
    AppState s = App.getState();
    if (login_pwd.getText().isBlank() || s.encrstate() == null || s.encrypted() == null || !s.encrypted().exists()) {
      if (login_pwd.getText().isBlank()) {
        logErr("Password is empty :(");
      }
      if (s.encrstate() == null) {
        logErr("EncrState not selected!");
      }
      if (s.encrypted() == null) {
        logErr("Encrypted file not selected!");
      } else if (!s.encrypted().exists()){
        logErr("Encrypted file not found!");
      }
      return;
    }

    try {
      var pwds = FileManager.getEntryListFromFile(login_pwd.getText(), s.encrstate(), s.encrypted());
      s.setPwds(pwds)
        .setPassword(login_pwd.getText())
        .setLocal(true)
        .setLogged(true);
      App.setRoot("main_screen");
    } catch (IOException e) {
      logErr("Encrypted file not found lul");
    } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e ) {
      logErr("Decryption gone wrong (hot)");
      logErr(e.toString());
    } catch (InvalidKeyException e) {
      errorMsg.setOpacity(1);
      errorMsg.setText("Wrong password");
      logErr("Wrong password supplied");
    }
  }
}
