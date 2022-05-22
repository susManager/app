package susmanager;

import fundur.systems.lib.Entry;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainScreen implements Initializable {

    private static ArrayList<Entry> pass = new ArrayList<>();

    
    @FXML private ListView<Entry> listView1 = new ListView<>();
    @FXML private ImageView nothing_found;
    @FXML private TextField searchBar1;

    /**
     * the
     * @param url - is ignored
     * @param resourceBundle + didn't ask + don't care + ratio
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView1.getItems().addAll(pass);
    }

    @FXML private void switchToSettings() throws IOException {
        App.setRoot("settings");
    }

    @FXML private void switchToAddPassword() throws IOException {
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

    @FXML private void playThudSound () {
        App.playThudSound();
    }

    private List<Entry> searchList(
            String searchWords,
            List<Entry> results
    ) {
        nothing_found.setOpacity(0);

        List<String> searchWordsArray = Arrays.asList(
                searchWords.trim().split(" ")
        );

        results =
                results
                        .stream()
                        .filter(
                                input -> {
                                    return searchWordsArray
                                            .stream()
                                            .allMatch(
                                                    word -> input.name().toLowerCase().contains(word.toLowerCase())
                                            );
                                }
                        )
                        .collect(Collectors.toList());

        if (results.size() == 0) {
            nothing_found.setOpacity(1);
        }
        return results;
    }

    /**
     * populates the static variable pass where the passwords are stored with default values
     */
    public static void setupPasswords() {
        pass = new ArrayList<>(Arrays.asList(
                new Entry("Steam", "steamUser", "steamPassword", 1),
                new Entry("Discord", "discordUser", "discordPassword", 1),
                new Entry("Teams", "teamsUser", "teamsPassword", 1),
                new Entry("Google", "googleUser", "googlePassword", 1)
        ));
    }

    public static void addPassword(Entry newEntry) {
        pass.add(newEntry);
    }
}
