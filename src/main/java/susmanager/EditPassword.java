package susmanager;

import fundur.systems.lib.Entry;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EditPassword implements Initializable {

  private static Entry pwd;

  @FXML
  private Pane pane;

  @FXML
  private TextField not_all_info;
  @FXML
  private TextField name, user, password, notes;
  @FXML
  private ImageView settingsWheel;

  @Override
  public void initialize(URL _url, ResourceBundle _resourceBundle) {
    name.setText(pwd.name());
    user.setText(pwd.usr());
    password.setText(pwd.pwd());
    notes.setText(pwd.notes());
    pane.setOnKeyPressed(e -> {
      if (e.getCode().equals(KeyCode.ESCAPE)) {
        try {
          return2Main();
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      }});
  }

  @FXML
  private void playThudSound() {
    App.playThudSound();
  }

  @FXML
  private void switchToSettings() throws IOException {
    App.setRoot("settings");
  }

  @FXML
  private void return2Main() throws IOException {
    App.setRoot("main_screen");
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
  private void savePassword() throws IOException {
    pwd.setName(name.getText());
    pwd.setUsr(user.getText());
    pwd.setPwd(password.getText());
    pwd.setNotes(password.getText());
    pwd.setTimestamp(System.currentTimeMillis() / 1000);
    return2Main();
  }

  @FXML
  private void onDeletePwd() throws IOException {
    App.getState().setPwds(
            App.getState().pwds()
                    .stream()
                    .filter(e ->
                      !(e.toString().equals(pwd.toString())))
                    .collect(Collectors.toList())
    );
    return2Main();
  }

  public static void setPwd(Entry e) {
    EditPassword.pwd = e;
  }

  public static boolean isNull() {
    return pwd == null;
  }
}
