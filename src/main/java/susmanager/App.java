package susmanager;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Stack;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {

  private static Scene scene;
  private static Stack<String> fxmlStack;

  private static MediaPlayer musicPlayer;
  private static boolean isPlaying;


  @Override
  public void start(Stage stage) throws IOException {
    setupAudio();
    fxmlStack = new Stack<>();
    fxmlStack.push("splash_screen");
    setupStack();
    scene = new Scene(loadFXML("splash_screen"), 350, 550);
    stage.setResizable(false);
    stage.setTitle("susManager");
    stage
            .getIcons()
            .add(new Image(("file:src/main/resources/susManager_logo.png")));
    stage.setScene(scene);
    stage.show();
    setupStage(stage);
    playThudSoundShort();
  }

  private static void setupStack() {
    fxmlStack = new Stack<>();
    fxmlStack.push("splash_screen");
  }

  private static void setupStage(Stage stage) {
    stage.setResizable(false);
    stage.setTitle("susManager");
    stage.getIcons().add(new Image(("file:src/main/resources/susManager_logo.png")));
    stage.setScene(scene);
    stage.show();
  }

  private static void setupAudio() {
    Media sound = new Media(getTopRes("background_music.mp3"));
    musicPlayer = new MediaPlayer(sound);
    isPlaying = false;
  }

  static void setRoot(String fxml) throws IOException {
    fxmlStack.push(fxml);
    scene.setRoot(loadFXML(fxml));
  }

  private static String getTopRes(String name) {
    return App.class.getResource("/" + name).toString();
  }

  /**
   * by pushing the latest scene onto the stack
   * when going back the 2nd latest is retrieved
   * @throws IOException if resource not found
   */
  static void popBack() throws IOException {
    fxmlStack.pop();
    scene.setRoot(loadFXML(fxmlStack.peek()));
  }

  private static Parent loadFXML(String fxml) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(
      App.class.getResource(fxml + ".fxml")
    );
    return fxmlLoader.load();
  }

  static void playThudSound() {
    Media sound = new Media(getTopRes("vine_boom.wav"));
    MediaPlayer mediaPlayer = new MediaPlayer(sound);
    mediaPlayer.play();
  }

  static void playThudSoundShort() {
    Media sound = new Media(getTopRes("vine_boom_short.wav"));
    MediaPlayer mediaPlayer = new MediaPlayer(sound);
    mediaPlayer.play();
  }

  static void playNotification() {
    Media sound = new Media(getTopRes("notification_sound.mp3"));
    MediaPlayer mediaPlayer = new MediaPlayer(sound);
    mediaPlayer.play();
  }

  static void playAllahMode() {
    Media sound = new Media(getTopRes("allah_mode.mp3"));
    MediaPlayer mediaPlayer = new MediaPlayer(sound);
    mediaPlayer.play();
  }

  static void toggleBackgroundMusic() {
    if (isPlaying) {
      musicPlayer.stop();
    } else {
      musicPlayer.play();
      musicPlayer.setOnEndOfMedia(() -> {
        musicPlayer.seek(Duration.ZERO);
        musicPlayer.play();
      });
    }
    isPlaying = !isPlaying;
  }

  public static void main(String[] args) {
    launch();
  }
}
