package susmanager;

import javafx.animation.FadeTransition;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class SplashScreen implements Initializable {
    @FXML
    private Pane logo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Random ran = new Random();
        long ranNum = ran.nextInt(1000) + 500;
        System.out.println("ranNum: " + ranNum);
        ranNum = 3000L;
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(ranNum));
        fade.setFromValue(0.1);
        fade.setToValue(1);
        fade.setNode(logo);
        fade.play();
        delay(
                ranNum,
                () -> {
                    try {
                        App.setRoot("login");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    @FXML
    public void playThudSound(MouseEvent ignored) {
        App.playThudSound();
    }

    public static void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<>() {
            @Override
            protected Void call()  {
                try {
                    Thread.sleep(millis);
                } catch (InterruptedException ignored) {}
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
    }
}
