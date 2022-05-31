package susmanager;

import fundur.systems.lib.Entry;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainScreen implements Initializable {

  private static ArrayList<Entry> pass = new ArrayList<>();

  @FXML
  private ListView<Entry> listView1 = new ListView<>();

  @FXML
  private ImageView nothing_found;

  @FXML
  private ImageView allah;

  @FXML
  private TextField searchBar1;

  /**
   * the
   * @param url - is ignored
   * @param resourceBundle + didn't ask + don't care + ratio + L bozo
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    listView1.setCellFactory(new EntryCellFactory());
    listView1.getItems().addAll(pass);

  }

  @FXML
  private void switchToSettings() throws IOException {
    App.setRoot("settings");
  }

  @FXML
  private void switchToAddPassword() throws IOException {
    App.setRoot("add_password");
  }

  /**
   * on action fill the searchList with all the results
   * @param _ignored being the ((Event)) either action triggered [enter] or some key eg. [a]
   */
  @FXML
  void search(Event _ignored) {
    listView1.getItems().clear();
    listView1.getItems().addAll(searchList(searchBar1.getText(), pass));
  }

  @FXML
  private void playThudSound() {
    App.playThudSound();
  }

  private List<Entry> searchList(String searchWords, List<Entry> results) {
    allah.setOpacity(0);
    nothing_found.setOpacity(0);

    List<String> searchWordsArray = Arrays.asList(
      searchWords.trim().split(" ")
    );

    results =
      results
        .stream()
        .filter(
          input -> searchWordsArray
            .stream()
            .allMatch(
              word -> input.name().toLowerCase().contains(word.toLowerCase())
            )
        )
        .collect(Collectors.toList());

    if (results.size() == 0) {
      nothing_found.setOpacity(1);
    }
    if (searchWords.equals("allah")) {
      App.playAllahMode();
      allah.setOpacity(1);
    }
    return results;
  }

  /**
   * populates the static variable pass where the passwords are stored with default values
   */
  public static void setupPasswords() {
    pass =
      new ArrayList<>(
        Arrays.asList(
          new Entry("Steam", "steamUser", "steamPassword", "notes", 1),
          new Entry("Discord", "discordUser", "discordPassword", "notes", 1),
          new Entry("Teams", "teamsUser", "teamsPassword", "notes", 1),
          new Entry("Google", "googleUser", "googlePassword", "notes", 1)
        )
      );
  }

  public static void setupPasswords(List<Entry> list) {
    pass = new ArrayList<>(list);
  }

  public static void addPassword(Entry newEntry) {
    pass.add(newEntry);
  }

  public static class EntryCellFactory implements Callback<ListView<Entry>, ListCell<Entry>> {
    @Override
    public ListCell<Entry> call(ListView<Entry> entryListView) {
      return new ListCell<Entry>() {
        @Override
        public void updateItem(Entry entry, boolean empty) {
          super.updateItem(entry, empty);
          if (entry == null || empty) {
            setText(null);
          } else {
            setText(entry.name());
          }
          setStyle("styleClass=cell");
        }


      };
    }
  }
}
