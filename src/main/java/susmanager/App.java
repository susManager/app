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

public class App extends Application {

  private static Scene scene;
  private static Stack<String> fxmlStack;

  @Override
  public void start(Stage stage) throws IOException {
    fxmlStack = new Stack<>();
    fxmlStack.push("splash_screen");
    scene = new Scene(loadFXML("splash_screen"), 350, 550);
    stage.setResizable(false);
    stage.setTitle("susManager");
    stage
      .getIcons()
      .add(new Image(("file:src/main/resources/susManager_logo.png")));
    stage.setScene(scene);
    stage.show();
    playThudSoundShort();
    Random ran = new Random();
    long ranNum = ran.nextInt(500, 1500);
    System.out.println("ranNum: " + ranNum);
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

  static void setRoot(String fxml) throws IOException {
    fxmlStack.push(fxml);
    scene.setRoot(loadFXML(fxml));
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
    String musicFile = "src/main/resources/vine_boom.wav";

    Media sound = new Media(new File(musicFile).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(sound);
    mediaPlayer.play();
  }

  static void playThudSoundShort() {
    String musicFile = "src/main/resources/vine_boom_short.wav";

    Media sound = new Media(new File(musicFile).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(sound);
    mediaPlayer.play();
  }

  static void playBackgroundMusic() {
    String musicFile = "src/main/resources/background_music.mp3";

    Media sound = new Media(new File(musicFile).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(sound);
    mediaPlayer.play();
  }

  public static void delay(long millis, Runnable continuation) {
    Task<Void> sleeper = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        try {
          Thread.sleep(millis);
        } catch (InterruptedException e) {}
        return null;
      }
    };
    sleeper.setOnSucceeded(event -> continuation.run());
    new Thread(sleeper).start();
  }

  public static void main(String[] args) {
    launch();
  }
}
