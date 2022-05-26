package susmanager;

import fundur.systems.lib.Entry;
import fundur.systems.lib.Manager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.crypto.BadPaddingException;
import java.io.IOException;
import java.util.List;

public class Login {

  @FXML
  private TextField wrong_password_or_username, no_password_entered, account_created, no_username_entered, login_username, login_pwd;

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
  private void checkPassword() {
    wrong_password_or_username.setOpacity(0);
    no_password_entered.setOpacity(0);
    account_created.setOpacity(0);
    no_username_entered.setOpacity(0);

    try {
      List<Entry> list = Manager.decrypt(login_username.getText(), login_pwd.getText());
      MainScreen.setupPasswords(list);
      switchToMainScreen();
    } catch (BadPaddingException _ignored) {
      wrong_password_or_username.setOpacity(1);
    } catch (NumberFormatException e) {
      if (e.getMessage().equals("For input string: \"nothing found\"")) {
        System.out.println("user not found");
      } else {
        throw e;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    wrong_password_or_username.setOpacity(1);
  }

  @FXML
  private void switchToMainScreen() throws IOException {
    MainScreen.setupPasswords();
    App.setRoot("main_screen");
  }

  @FXML
  void createAccount() {
    wrong_password_or_username.setOpacity(0);
    no_password_entered.setOpacity(0);
    account_created.setOpacity(0);
    no_username_entered.setOpacity(0);

    if (login_username.getText().length() > 0) {
      if (login_pwd.getText().length() > 0) {
        account_created.setOpacity(1);
      } else {
        System.out.println("Password not long enough!");
        no_password_entered.setOpacity(1);
      }
    } else {
      System.out.println("Username not long enough!");
      no_username_entered.setOpacity(1);
    }
  }
}
