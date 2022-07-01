package susmanager;

import fundur.systems.lib.*;
import fundur.systems.lib.sec.Security;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static susmanager.App.logErr;


public class Settings implements Initializable {

  @FXML
  private TextField currTheme;

  @FXML
  private Button logoutBtn;
  @FXML
  private ImageView trollFace;

  @FXML
  private ChoiceBox<String> selectTheme;

  @FXML
  private void playThudSound() {
    App.playThudSound();
  }

  @FXML
  private void playBackgroundMusic() {
    App.toggleBackgroundMusic();
  }

  @FXML
  private void playAllahMode() {
    App.playAllahMode();
  }

  @FXML
  private void playNotification() {
    App.playNotification();
  }

  @FXML
  private void switchBack() throws IOException {
    App.popBack();
  }

  @FXML
  private void turnOff() {
    trollFace.setOpacity(1);
  }

  @FXML
  private void switchToLogin() throws IOException {
    App.setRoot("login");
  }

  @FXML
  private void triggerLogout() throws IOException {
    Settings.logout();
  }

  public static void logout() throws IOException {
    AppState s = App.getState();

    if (!s.debug()) {
      if (s.local()) {
        try {
          FileManager.saveToFile(s.pwds(), s.encrstate(), s.encrypted(), s.password());
        } catch (IOException e) {
          logErr("unable to open file!");
        } catch (Exception e) {
          logErr("at this point, how did this happen?");
          logErr(e.toString());
        }
      } else {
        JSONObject json = Manager.list2JSONObject(s.pwds());
        try {
          String encr = Manager.encrypt(json, s.password(), s.encrstate());
          NetManager.postLatestToServer(s.user(), encr);
        } catch (IOException e) {
          logErr("unable to connect to server!");
        } catch (Exception e) {
          logErr("at this point, how did this happen?");
          logErr(e.toString());
        }
      }
    }

    App.getState().setPwds(new ArrayList<>())
            .setPassword("")
            .setEncrstate(null)
            .setUser("")
            .setEncrypted(null)
            .setUrl("")
            .setLogged(false)
            .setDebug(false);
    App.setRoot("login_remote");
  }

  @FXML
  @Deprecated
  private void exportToEncr() {
    AppState s = App.getState();
    File f = new File(s.user() + "-encrypted.sus");
    if (f.exists()) {
      f = new File(Security.hash(s.user()) + "-encrypted-" + (System.currentTimeMillis() / 1000) + ".sus");
    }

    try {
      FileManager.saveToFile(s.pwds(), s.encrstate(), f, s.password());
    } catch (IOException e) {
      logErr("unable to open file!");
    } catch (Exception e) {
      logErr("at this point, how did this happen?");
      logErr(e.toString());
    }

    File e = new File(Security.hash(s.user()) + "-" + "config.json");
    try {
      FileManager.saveFile(s.encrstate().toString(), e);
    } catch (IOException i) {
      logErr("unable to open file!");
    } catch (Exception i) {
      logErr("at this point, how did this happen?");
      logErr(i.toString());
    }
  }

  @FXML
  private void tryExport() {
    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Export Passwords?", ButtonType.YES, ButtonType.NO);
    a.setTitle("Export confirmation");
    a.setHeaderText("Are you sure?");
    a.setContentText("This will export your passwords in plain text making them possible to be compromised!");
    a.showAndWait();
    if (a.getResult().equals(ButtonType.YES)) {
      export();
    }
  }

  private void export() {
    AppState s = App.getState();
    File f = new File(s.user() + "-encrypted.sus");
    if (f.exists()) {
      f = new File(s.user() + "-encrypted-" + (System.currentTimeMillis() / 1000) + ".sus");
    }

    try {
      JSONObject obj = Manager.list2JSONObject(s.pwds());
      FileManager.saveFile(obj.toString(), f);
    } catch (IOException e) {
      logErr("unable to open file!");
    } catch (Exception e) {
      logErr("at this point, how did this happen?");
      logErr(e.toString());
    }
  }

  @FXML
  private void importF () {
    AppState s = App.getState();

    FileChooser fc = new FileChooser();
    fc.setTitle("Select Plaintext");
    fc.setInitialDirectory(new File("."));
    File f = fc.showOpenDialog(logoutBtn.getScene().getWindow());
    if (f == null || !f.exists())
      return;
    try {
      JSONObject jsonNew = new JSONObject(FileManager.loadFile(f));
      JSONObject jsonCurr = Manager.list2JSONObject(s.pwds());

      List<Entry> merged = Manager.merge(jsonCurr, jsonNew);
      s.setPwds(merged);
      App.setRoot("main_screen");
    } catch (FileNotFoundException e) {
      logErr("file not found lmao");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  private void color_switchToDefault() {
    App.loadTheme("default");
  }

  @FXML
  private void color_switchToNord() {
    App.loadTheme("nord");
  }

  @FXML
  private void color_switchToTokyoNight() {
    App.loadTheme("tokyo_night");
  }

  @FXML
  private void color_switchToCafe() {
    App.loadTheme("gruvbox");
  }

  @FXML
  private void color_switchToLight() {
    App.loadTheme("light");
  }

  @FXML
  private void color_switchToLightPeach() {
    App.loadTheme("light_peach");
  }

  @FXML
  private void color_switchToDark() {
    App.loadTheme("dark");
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    initThemes();
    if (!App.getState().logged()) {
      logoutBtn.setOnAction(null);
      logoutBtn.setOpacity(0);
      logoutBtn.setDisable(true);
    }
  }

  private void initThemes() {
    selectTheme.getItems().add("Default");
    selectTheme.getItems().add("Nord");
    selectTheme.getItems().add("Tokyo Night");
    selectTheme.getItems().add("Gruvbox");
    selectTheme.getItems().add("Light");
    selectTheme.getItems().add("Light Peach");
    selectTheme.getItems().add("Dark");
    selectTheme.setOnAction(event -> {switchThemePre();});
  }

  @FXML
  private void switchThemePre() {
    switch (selectTheme.getValue().toString()){
      case "Default":
        color_switchToDefault();
        break;
      case "Nord":
        color_switchToNord();
        break;
      case "Tokyo Night":
        color_switchToTokyoNight();
        break;
      case "Gruvbox":
        color_switchToCafe();
        break;
      case "Light":
        color_switchToLight();
        break;
      case "Light Peach":
        color_switchToLightPeach();
        break;
      case "Dark":
        color_switchToDark();
        break;
    }
  }
}