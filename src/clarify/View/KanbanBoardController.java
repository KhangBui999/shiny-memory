/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clarify.View;

import clarify.Util.PageSwitchHelper;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class for KanbanBoard
 * TODO:
 * - Figure out what's going to be displayed on task
 * - Load tasks into listView
 * - Sort tasks based on date
 * @author khang
 */
public class KanbanBoardController implements Initializable {

    PageSwitchHelper p = new PageSwitchHelper();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
