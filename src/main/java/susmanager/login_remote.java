package susmanager;

import fundur.systems.lib.*;
import fundur.systems.lib.sec.EncrState;
import fundur.systems.lib.sec.Security;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static fundur.systems.lib.Manager.encrypt;
import static susmanager.App.logErr;

public class login_remote {

  @FXML
  private TextField  account_created, errorMsg, url, lg_usr, lg_pwd;

  @FXML
  private ImageView settingsWheel, switchToLoginLocal;

  /**
   * Plays the vine boom effect when the logo is clicked
   */
  @FXML
  private void playThudSound() {
    App.playThudSound();
  }
  
  @FXML
  private void hoverEffect(){
  settingsWheel.setEffect(new Glow(1));
  }

  @FXML
  private void hoverEffectStop(){
  settingsWheel.setEffect(new Glow(0));
  }

  @FXML
  private void hoverEffectSwitchLocalLogin(){
  switchToLoginLocal.setEffect(new Glow(1));
  }

  @FXML
  private void hoverEffectSwitchLocalLoginStop(){
    switchToLoginLocal.setEffect(new Glow(0));
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
  private void switchToLoginLocal() throws IOException {
    App.setRoot("login_local");
  }

  private boolean pre() {
    account_created.setOpacity(0);
    errorMsg.setOpacity(0);

    if (url.getText().isBlank() || lg_pwd.getText().isBlank() || lg_usr.getText().isBlank()) {
      var err = "";
      if (url.getText().isBlank()) {
        logErr("url blank!");
        err += "url blank!\n";
      }
      if (lg_pwd.getText().isBlank()) {
        logErr("pwd blank!");
        err += "pwd blank!\n";
      }
      if (lg_usr.getText().isBlank()) {
        logErr("usr name blank!");
        err += "usr name blank!\n";
      }
      errorMsg.setText(err);
      errorMsg.setOpacity(1);
      return true;
    }
    return false;
  }

  @FXML
  private void tryLogin() {
    if (pre())
      return;

    var url = this.url.getText();

    NetManager.serverURL = url + (url.endsWith( "/") ?  "" : "/");

    try {
      List<Entry> list = Manager.decrypt(lg_usr.getText(), lg_pwd.getText());
      App.getState()
              .setEncrstate(NetManager.getEncrStateFromServer(lg_usr.getText()))
              .setPassword(lg_pwd.getText())
              .setUser(lg_usr.getText())
              .setPwds(list)
              .setLogged(true)
              .setLocal(false);
      App.setRoot("main_screen");
    } catch (NumberFormatException e) {
      if (e.getMessage().equals("For input string: \"nothing found\"")) {
        logErr("user not found");
      } else {
        throw e;
      }
    } catch (Exception e) {
      logErr(e.getMessage());
    }
  }

  @FXML
  private void defaultMainScreen() throws IOException {
    MainScreen.setupDefaultPasswords();
    App.getState()
            .setLogged(true)
            .setDebug(true);
    App.setRoot("main_screen");
  }

  @FXML
  private void tryCreateAccount() {
    if (pre())
      return;

    var url = this.url.getText();

    NetManager.serverURL = url + (url.endsWith( "/") ?  "" : "/");

    try {
      if (NetManager.exists(lg_usr.getText())) {
        logErr("user exists");
        errorMsg.setText("User exists!");
        errorMsg.setOpacity(1);
        return;
      }
      EncrState state = Security.generateNewEncrstate();
      NetManager.postEncrStateToServer(lg_usr.getText(), state.toJSON().toString());

      App.getState()
              .setEncrstate(NetManager.getEncrStateFromServer(lg_usr.getText()))
              .setPassword(lg_pwd.getText())
              .setUser(lg_usr.getText())
              .setPwds(new ArrayList<>())
              .setLogged(true)
              .setLocal(false);
      App.setRoot("main_screen");
    } catch (NumberFormatException e) {
      if (e.getMessage().equals("For input string: \"nothing found\"")) {
        logErr("user not found");
      } else {
        throw e;
      }
    } catch (Exception e) {
      logErr(e.getMessage());
    }
  }

  public static void main(String[] args) throws Exception {
    //Just for setup purposes
    NetManager.serverURL = "http://fundur.systems:6969/";
    String file = FileManager.loadFile("config.json");
    NetManager.postEncrStateToServer("fridolin", (new JSONObject(file)).toString());
    NetManager.postLatestToServer("fridolin", encrypt(Dummy.getDefaultDummyJSON(), "", Security.hash("fridolin"), "iHaveAids69"));
  }
}
