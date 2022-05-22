package susmanager;

import java.io.File;
import java.io.IOException;
import java.util.Stack;

import javafx.application.Application;
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
    scene = new Scene(loadFXML("login"), 350, 550);
    stage.setResizable(false);
    stage.setTitle("susManager");
    stage
      .getIcons()
      .add(new Image(("file:src/main/resources/susManager_logo.png")));
    stage.setScene(scene);
    stage.show();
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

  public static void main(String[] args) {
    launch();
  }
}
