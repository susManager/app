package susmanager;

import fundur.systems.lib.*;
import fundur.systems.lib.sec.Security;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import static fundur.systems.lib.Manager.encrypt;
import static susmanager.App.logErr;

public class login_remote {

  @FXML
  private TextField  account_created, errorMsg, url, lg_usr, lg_pwd;

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
  private void switchToLoginLocal() throws IOException {
    App.setRoot("login_local");
  }

  @FXML
  private void checkPassword() {
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
      return;
    }

    var url = this.url.getText();

    NetManager.serverURL = url + (url.endsWith( "/") ?  "" : "/");

    try {
      List<Entry> list = Manager.decrypt(lg_usr.getText(), lg_pwd.getText());
      App.getState()
              .setEncrstate(NetManager.getEncrStateFromServer(lg_usr.getText()))
              .setPassword(lg_pwd.getText())
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
  void createAccount() {

    account_created.setOpacity(0);


    if (lg_usr.getText().length() > 0) {
      if (lg_pwd.getText().length() > 0) {
        account_created.setOpacity(1);
      } else {
        System.out.println("Password not long enough!");

      }
    } else {
      System.out.println("Username not long enough!");

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
