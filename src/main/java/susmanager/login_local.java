package susmanager;

import fundur.systems.lib.FileManager;
import fundur.systems.lib.sec.EncrState;
import fundur.systems.lib.sec.Security;
import javafx.event.ActionEvent;
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
import java.util.ArrayList;

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
    errorMsg.setOpacity(0);

    FileChooser fc = new FileChooser();
    fc.setInitialDirectory(new File("."));
    fc.setTitle("Choose your config");
    File f = fc.showOpenDialog(login_pwd.getScene().getWindow());

    if (f == null || !f.exists()) {
      errorMsg.setOpacity(1);
      errorMsg.setText("config file not found");
      logErr("config file not found");
      return;
    }

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
    errorMsg.setOpacity(0);

    FileChooser fc = new FileChooser();
    fc.setTitle("Choose the encrypted file");
    fc.setInitialDirectory(new File("."));
    File f = fc.showOpenDialog(login_pwd.getScene().getWindow());
    if (f == null || !f.exists()) {
      errorMsg.setOpacity(1);
      errorMsg.setText("Encrypted file not found!");
      logErr("Encrypted file not found!");
    } else
      App.getState().setEncrypted(f);
  }

  private boolean pre() {
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
      return true;
    }
    return false;
  }

  @FXML
  private void localLogin() {
    if (pre())
      return;

    AppState s = App.getState();
    try {
      var pwds = FileManager.getEntryListFromFile(login_pwd.getText(), s.encrstate(), s.encrypted());
      s.setPwds(pwds)
        .setPassword(login_pwd.getText())
        .setLocal(true)
        .setLogged(true);
      App.setRoot("main_screen");
    } catch (IOException e) {
      logErr("Encrypted file not found lul");
    } catch (BadPaddingException e) {
      logErr("wrong password");
    } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException e) {
      logErr("Decryption gone wrong (hot)");
      logErr(e.toString());
    } catch (InvalidKeyException e) {
      errorMsg.setOpacity(1);
      errorMsg.setText("Wrong password");
      logErr("Wrong password supplied");
    }
  }

  @FXML
  private void createLocal() throws IOException {
    if (login_pwd.getText().isBlank())
      return;

    EncrState state = Security.generateNewEncrstate();
    String hsh = Security.hash(String.valueOf(System.currentTimeMillis() / 1000));

    FileManager.saveFile(state.toString(), new File(hsh + "-config.json"));

    App.getState()
            .setEncrstate(state)
            .setPwds(new ArrayList<>())
            .setPassword(login_pwd.getText())
            .setLocal(true)
            .setLogged(true)
            .setEncrypted(new File(hsh + "-encrypted.sus"))
            .setDebug(false);
    App.setRoot("main_screen");
  }
}
