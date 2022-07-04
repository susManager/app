package susmanager;

import java.io.IOException;
import java.util.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class App extends Application {

  private static Scene scene;
  private static Stack<String> fxmlStack;

  private static MediaPlayer musicPlayer;
  private Timer timer;
  private static boolean isPlaying;

  private static String currentTheme = "";

  private static AppState state;

  @Override
  public void start(Stage stage) throws IOException {
    setupAudio();
    fxmlStack = new Stack<>();
    fxmlStack.push("splash_screen");
    setupStack();
    scene = new Scene(loadFXML("splash_screen"), 350, 550);
    //TODO:
    // add close handler
    // https://stackoverflow.com/questions/26619566/javafx-stage-close-handler
    timer = new Timer();
    loadCSS();
    initCSS();
    setupStage(stage);
    setupCloseHandler();
    playThudSoundShort();
    state = new AppState();
  }

  private void setupCloseHandler() {
    scene.getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, (_ignored) -> {
      try {
        Settings.logout();
        musicPlayer.stop();
        timer.cancel();
      } catch (IOException e) {
        System.out.println(e); //TODO: better catch
      }
    });
  }

  private static void loadCSS() {
    scene.getStylesheets().add(getTopRes("css/theming.css"));
    scene.getStylesheets().add(getTopRes("css/settings.css"));
    scene.getStylesheets().add(getTopRes("css/login.css"));
    scene.getStylesheets().add(getTopRes("css/main_screen.css"));
    scene.getStylesheets().add(getTopRes("css/add_pwd.css"));
    scene.getStylesheets().add(getTopRes("css/themes/default.css"));
  }

  public static void loadTheme(String name) {
    String newTheme = getTopRes("css/themes/" + name + ".css");
    if (newTheme == null || newTheme.isEmpty()) {
      System.out.println("Theme " + name + " not found!");
    } else {
      scene.getStylesheets().removeAll(currentTheme);
      scene.getStylesheets().add(newTheme);
      currentTheme = newTheme;
    }
  }

  private static void setupStack() {
    fxmlStack = new Stack<>();
    fxmlStack.push("splash_screen");
  }

  private static void setupStage(Stage stage) {
    stage.setResizable(false);
    stage.setTitle("susManager");
    stage
      .getIcons()
      .add(new Image(("file:src/main/resources/susManager_logo.png")));
    stage.setScene(scene);
    stage.show();
  }

  private void initCSS() {
 /*   Random random = new Random();                           //TEMPORARLY REMOVED FOR PRODUCTION
    if (true) {
      MediaPlayer m = new MediaPlayer(new Media(getTopRes("bgm_mc.mp3")));
      scheduleRun(m::play, 42 * 1000);
      m.setOnEndOfMedia(
        () ->
          scheduleRun(
            () -> {
              m.seek(Duration.ZERO);
              m.play();
            },
            (long) (random.nextInt(360) + 180) * 1000
          )
      );
    }
*/
  }

  /**
   *
   * @param task sus
   * @param delay delay in milliseconds
   */
  public void scheduleRun(Runnable task, long delay) {
    timer
    .schedule(
        new TimerTask() {
          @Override
          public void run() {
            task.run();
          }
        },
        delay
      );
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

  public static AppState getState() {
    return state;
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
    playSound(new Media(getTopRes("notification_sound.mp3")));
  }

  static void playAllahMode() {
    playSound(new Media(getTopRes("allah_mode.mp3")));
  }

  static void toggleBackgroundMusic() {
    if (isPlaying) {
      musicPlayer.stop();
    } else {
      loopSound(new Media(getTopRes("background_music.mp3")));
    }
    isPlaying = !isPlaying;
  }

  private static void loopSound(Media sound) {
    musicPlayer.stop();
    musicPlayer = new MediaPlayer(sound);
    musicPlayer.play();
    musicPlayer.setOnEndOfMedia(
      () -> {
        musicPlayer.seek(Duration.ZERO);
        musicPlayer.play();
      }
    );
  }

  private static void playSound(Media sound) {
    musicPlayer.stop();
    musicPlayer = new MediaPlayer(sound);
    musicPlayer.play();
  }

  public static String getCurrentTheme() {
    return currentTheme;
  }

  public static void logErr(Object o) {
    System.out.println(o);
  }

  public static void main(String[] args) {
    launch();
  }
}
