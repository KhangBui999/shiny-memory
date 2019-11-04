/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clarify.View;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author khang
 */
public class AddTaskController implements Initializable {

    @FXML
    private ComboBox titleField;
    @FXML
    private Label titleError;
    
    @FXML
    private TextArea descArea;
    
    @FXML
    private DatePicker doField;
    @FXML
    private Label doError;
    
    @FXML
    private DatePicker dueField;
    @FXML
    private Label dueError;
    
    @FXML
    private Slider prioritySlider;
    @FXML
    private TextField priorityField;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        priorityField.textProperty().bind(prioritySlider.valueProperty().asString());
    }
    
    public void handleCreateButton() {
        if(checkError() == true){
            //todo sql statement
        }
    }
    
    //Catches any inappropriate input and warns user
    public boolean checkError() {
        boolean errorStatus = true;
        
        doError.setText("Error: Please select a date");
        dueError.setText("Error: Please select a date");
        
        if(titleField.getValue() == null || titleField.getValue().equals("")){
            titleError.setVisible(true);
            errorStatus = false;
        } else {
            titleError.setVisible(false);
        }
        
        if(doField.getValue() == null){
            doError.setVisible(true);
            errorStatus = false;
        } else {
            doError.setVisible(false);
        }
        
        if(dueField.getValue() == null){
            dueError.setVisible(true);
            errorStatus = false;
        } else {
            dueError.setVisible(false);
        }
        
        if(errorStatus == true) {
            errorStatus = dateCheck();
        }
        
        return errorStatus;
    }
    
    //Date checker to ensure do-date is before due-date
    public boolean dateCheck() {
        LocalDate doDate = doField.getValue();
        LocalDate dueDate = dueField.getValue();
        
        if(doDate.isAfter(dueDate)){
            doError.setText("Error: Do-date must be BEFORE due-date");
            dueError.setText("Error: Do-date must be BEFORE due-date");
            doError.setVisible(true);
            dueError.setVisible(true);
            return false;
        } else {
            return true;
        }
    }
    
}
