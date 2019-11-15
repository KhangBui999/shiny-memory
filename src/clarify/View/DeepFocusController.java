/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clarify.View;

import clarify.Model.Task;
import clarify.Util.Database;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;

import javafx.util.Duration;

public class DeepFocusController implements Initializable {

    //create new DB
    Database d = new Database();

// create a listview in FXML called allList that contains task information
    @FXML
    private Label taskTitle;
    @FXML
    private Label taskDesc;

    @FXML
    private ComboBox moodBox;

    @FXML
    private ListView<Task> allList;

    @FXML
    private Label dateTime;
    ObservableList<Task> items = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle rb) {

        allList.setItems(items);
        try {
            getTaskFromDb();
            initClock();
            moodBox.getItems().addAll("Upbeat", "Rock", "Calm Piano");

        } catch (SQLException ex) {
            Logger.getLogger(DeepFocusController.class.getName()).log(Level.SEVERE, null, ex);
        }
        allList.setItems(this.items);
    }

    //create method to pull tasks from DB
    public void getTaskFromDb() throws SQLException {
        System.out.println("Task Title and Description");

        //establish conn to db
        Connection conn = DriverManager.getConnection("jdbc:sqlite:INFS2605.db");
        Statement st = conn.createStatement();

        //create new list called task to add titles and desc from DB
        ArrayList<Task> selectedTask = new ArrayList<>();

        String selectQuery = "SELECT * FROM TASKS";
        ResultSet rs = st.executeQuery(selectQuery);

        while (rs.next()) {
            System.out.println("Task Title Selected: " + rs.getString(1));
            items.add(new Task(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7)));
        }
    }

    //method to let user select tasks and view title and desc
    @FXML
    public void userClickedTask() {
        Task xyz = allList.getSelectionModel().getSelectedItem();

        taskTitle.setText("Focus on: " + xyz.getString(xyz.getTitleProperty()));
        taskDesc.setText("Description: " + xyz.getString(xyz.getDescriptionProperty()));

    }

    public void initClock() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(" dd/MM/yyyy "
                    + "HH:mm:ss");
            dateTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

    }

    public void userSelectedMood() {

        String output = moodBox.getSelectionModel().getSelectedItem().toString();
        if (output.equals("Upbeat")) {
            MusicPlaybackHelper.playMusic("Upbeat.mp3");
        } else if (output.equals("Rock")) {
            MusicPlaybackHelper.playMusic("Rock.mp3");
        } else if (output.equals("Calm Piano")) {
            MusicPlaybackHelper.playMusic("Calm.mp3");
        }

    }
}
