package susmanager;

import fundur.systems.lib.Entry;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainScreen implements Initializable {

  @FXML
  private Pane pane;

  @FXML
  private ListView<Entry> listView = new ListView<>();

  @FXML
  private ImageView nothing_found;

  @FXML
  private ImageView allah;

  @FXML
  private TextField searchBar1;

  @FXML
  private ImageView settingsWheel;

  private long last;

  /**
   * the
   * @param url - is ignored
   * @param resourceBundle + didn't ask + don't care + ratio + L bozo
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    listView.setCellFactory(new EntryCellFactory());
    listView.getItems().addAll(App.getState().pwds());
    listView.setOnMouseClicked(e -> {
      if ((System.currentTimeMillis() - last) < 500) {
        try {
          switchToEditPassword();
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      } else
        last = System.currentTimeMillis();
    });

    listView.setOnKeyPressed(k -> {
      switch (k.getCode()) {
        case SPACE:
        case ENTER:
          if (!EditPassword.isNull()) {
            try {
              switchToEditPassword();
            } catch (IOException e) {
              throw new RuntimeException(e);
            }
          }
          break;
      }
    });

    nothing_found.setDisable(true);
    allah.setDisable(true);
  }

  @FXML
  private void switchToSettings() throws IOException {
    App.setRoot("settings");
  }

  @FXML
  private void switchToAddPassword() throws IOException {
    App.setRoot("add_password");
  }

  @FXML
  private void switchToEditPassword() throws IOException {
    if (!EditPassword.isNull()) App.setRoot("edit_password");
  }
  
  @FXML
  private void hoverEffect(){
  settingsWheel.setEffect(new Glow(1));
  }

  @FXML
  private void hoverEffectStop(){
  settingsWheel.setEffect(new Glow(0));
  }

  /**
   * on action fill the searchList with all the results
   * @param _ignored being the ((Event)) either action triggered [enter] or some key eg. [a]
   */
  @FXML
  void search(Event _ignored) {
    listView.getItems().clear();
    listView.getItems().addAll(searchList(searchBar1.getText(), App.getState().pwds()));
  }

  @FXML
  private void playThudSound() {
    App.playThudSound();
  }

  private List<Entry> searchList(String searchWords, List<Entry> entries) {
    allah.setDisable(true);
    nothing_found.setDisable(true);

    allah.setOpacity(0);
    nothing_found.setOpacity(0);

    List<String> searchWordsArray = Arrays.asList(
      searchWords.trim().split(" ")
    );

    var results =
      entries
        .stream()
        .filter(
          input -> searchWordsArray
            .stream()
            .anyMatch(
              word -> input.name().toLowerCase().contains(word.toLowerCase())
            )
        )
        .collect(Collectors.toList());

    if (results.size() == 0) {
      nothing_found.setOpacity(1);
      nothing_found.setDisable(false);
    }
    if (searchWords.equals("allah")) {
      allah.setDisable(false);
      App.playAllahMode();
      allah.setOpacity(1);
    }
    return results;
  }

  /**
   * populates the static variable pass where the passwords are stored with default values
   */
  public static void setupDefaultPasswords() {
    App.getState().pwds().addAll(
      new ArrayList<>(
        Arrays.asList(
          new Entry("Steam", "steamUser", "steamPassword", "notes", 1),
          new Entry("Discord", "discordUser", "discordPassword", "notes", 1),
          new Entry("Teams", "teamsUser", "teamsPassword", "notes", 1),
          new Entry("Google", "googleUser", "googlePassword", "notes", 1)
        )
      ));
  }

  public static class EntryCellFactory implements Callback<ListView<Entry>, ListCell<Entry>> {
    @Override
    public ListCell<Entry> call(ListView<Entry> entryListView) {
      var cell = new ListCell<Entry>() {
        @Override
        public void updateItem(Entry entry, boolean empty) {
          super.updateItem(entry, empty);
          if (entry == null || empty) {
            setText(null);
          } else {
            setText(entry.name());
          }
        }
      };
      cell.setEditable(true);
      cell.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
        EditPassword.setPwd(cell.getItem());
      });
      return cell;
    }
  }

  public static class KeyHander<KeyEvent extends Event> implements  EventHandler<KeyEvent> {
    private ListView<Entry> l1;

    public KeyHander(ListView<Entry> ll) {
      l1 = ll;
    }

    @Override
    public void handle(KeyEvent keyEvent) {

    }
  }
}
