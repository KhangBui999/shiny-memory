/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clarify.View;

/*TODO:
* Category colour
* Edit button
*/

import clarify.Model.Task;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author khang
 */
public class TaskNoteController extends ListCell<Task> {

    private FXMLLoader loader;
    
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private Text taskInfo;
    
    @FXML
    private Button dateBtn;
    
    @FXML
    private Button status;
    
    @Override
    protected void updateItem(Task task, boolean empty){
        super.updateItem(task, empty);
        
        if (empty || task == null) {
            setText(null);
            setGraphic(null);
        }
        else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getResource("/clarify/View/TaskNote.fxml"));
                loader.setController(this);
                
                try {
                    loader.load();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            
            setText(null);
            this.taskInfo.setText(task.getString(task.getTitleProperty()));
            String date = convertDate(task.getString(task.getDueDateProperty()));
            this.dateBtn.setText(date);
            priorityStatus(task.getInt(task.getPriorityProperty()), task);
            setGraphic(this.anchorPane);
            }
        }
    }
    
    private void priorityStatus(int input, Task task) {
        this.status.setText("Priority (" +input+")");
        completeStatus(task);
        //TODO: Colour change based on priority
    }
    
    private void completeStatus(Task task){
        int completeId = task.getInt(task.getCompleteProperty());
        if(completeId == 0) {
            //Nothing happens :)
        }
        else if(completeId == 1) {
            this.status.setVisible(false);
        }
    }
     
    private String convertDate(String input) {
        String day = input.substring(8,10);
        String month = retrieveMonth(input.substring(5,7));
        return day+" "+month;
    }
    
    private String retrieveMonth(String input) {
        String month = null;
        switch(input) {
            case "01": month = "Jan"; break;
            case "02": month = "Feb"; break;
            case "03": month = "Mar"; break;
            case "04": month = "Apr"; break;
            case "05": month = "May"; break;
            case "06": month = "Jun"; break;
            case "07": month = "Jul"; break;
            case "08": month = "Aug"; break;
            case "09": month = "Sep"; break;
            case "10": month = "Oct"; break;
            case "11": month = "Nov"; break;
            case "12": month = "Dec"; break;
        }
        return month;
    }
}
