package susmanager;

import fundur.systems.lib.Entry;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class AddPassword {

    @FXML
    private TextField not_all_info;
    @FXML
    private TextField name, user, password, notes;
    @FXML
    private ImageView settingsWheel;

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
    private void hoverEffect() {
        settingsWheel.setEffect(new Glow(1));
    }

    @FXML
    private void hoverEffectStop() {
        settingsWheel.setEffect(new Glow(0));
    }

    @FXML
    private void addPassword() throws IOException {
        if (name.getText().isBlank() || user.getText().isBlank() || password.getText().isBlank()) {
            not_all_info.visibleProperty().set(true);
        }

        App.getState().pwds().add(
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
