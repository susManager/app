package susmanager;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class App extends Application {

  private static Scene scene;

  @Override
  public void start(Stage stage) throws IOException {
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
    scene.setRoot(loadFXML(fxml));
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
