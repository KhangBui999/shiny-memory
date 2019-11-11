/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clarify.View;

import clarify.Model.Task;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * Please do not touch this file in any way, shape or form If you need
 * rootLayout to do something, please message me on Messenger. (Insert smiley
 * face here)
 *
 * @author khang
 */
public class RootLayoutController {

    @FXML
    private BorderPane root;

    @FXML
    private AnchorPane content;

    private String page;

    //Initializes the page to load Home.fxml in BorderPane center
    public void initialize() {
        loadPage("Home.fxml");
    }

    public void setPage(String current) {
        this.page = current;
    }

    @FXML
    private void homeMenuPressed(ActionEvent event) {
        loadPage("Home.fxml");
    }

    @FXML
    private void kanbanMenuPressed(ActionEvent event) {
        loadPage("KanbanBoard.fxml");
    }

    @FXML
    private void deepMenuPressed(ActionEvent event) {
        loadPage("DeepFocus.fxml");
    }
    
    @FXML
    private void timeLoggingPressed(ActionEvent event) {
        loadPage("TimeLogging.fxml");
    } 

    //This method changes the BorderPane center to specified page location
    public void loadPage(String pageLocation) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(pageLocation));
            AnchorPane page = (AnchorPane) loader.load();
            root.setCenter(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //This method passes on value from Task in a controller to UpdateTask
    public void loadTaskUpdate(int id) throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("UpdateTask.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            UpdateTaskController u = loader.getController();
            u.setId(id);
            u.retrieveTaskFromDb();
            root.setCenter(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadTaskDelete(int id) throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("DeleteTask.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            DeleteTaskController d = loader.getController();
            d.setId(id);
            root.setCenter(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
