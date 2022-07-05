package susmanager;

import fundur.systems.lib.Entry;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EditPassword implements Initializable {

  private static Entry pwd;

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
    System.out.println(App.getState().pwds());
    return2Main();
  }

  @FXML
  private void onDeletePwd() throws IOException {
    App.getState().setPwds(
            App.getState().pwds()
                    .stream().filter(e ->
                      !(e.toString().equals(pwd.toString()))
                    ).collect(Collectors.toList())
    );
    return2Main();
  }

  public static void setPwd(Entry e) {
    EditPassword.pwd = e;
  }
}
