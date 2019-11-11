/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clarify.View;

import clarify.Util.Database;
import clarify.Util.PageSwitchHelper;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import static java.time.LocalDate.now;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author khang
 */
public class AddTaskController implements Initializable {

    private PageSwitchHelper p = new PageSwitchHelper();
    private Database d = new Database();
    
    @FXML
    private TextField titleField;
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
    
    @FXML
    private Label status;
    
    @FXML
    private GridPane gridPane;
    
    @FXML
    private Button btn;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        priorityField.textProperty().bind(prioritySlider.valueProperty().asString());
    }
    
    public void handleCreateButton() {
        if(checkError() == true){
            String title = titleField.getText();
            String desc = descArea.getText();
            String doDate = doField.getValue().toString();
            String dueDate = dueField.getValue().toString();
            int priority = (int)prioritySlider.getValue();
            int status = 0;
            try {
                String insert = "INSERT INTO TASKS (task_title, task_desc, do_date, due_date, priority, status) "
                        + "VALUES('" + title
                        + "','" + desc
                        + "','" + doDate
                        + "','" + dueDate
                        + "','" + priority
                        + "','" + status + "');";
                d.insertStatement(insert);
                System.out.println("SQL statement was inserted successfully");
                this.status.setText("Task was created successfully. Click 'BACK' to go to previous page.");
                this.gridPane.setVisible(false);
                this.btn.setVisible(false);
                this.status.setTextFill(Color.web("#00e500"));
                this.status.setVisible(true);
            }
            catch (SQLException e) {
                this.status.setText("Task failed to create due to a system SQL error. Please try again.");
                this.status.setTextFill(Color.web("#ff0000"));
                this.status.setVisible(true);
                System.out.println("SQL statement could not be inserted successfully");
            }
        }
    }
    
    public void handleBackButtonK(ActionEvent event) throws IOException {
        p.changeCenter(event, "/clarify/View/KanbanBoard.fxml");
    }
    
    public void handleBackButtonD(ActionEvent event) throws IOException {
        p.changeCenter(event, "/clarify/View/DeepFocus.fxml");
    }
    
    //Catches any inappropriate input and warns user
    public boolean checkError() {
        boolean errorStatus = true;
        
        doError.setText("Error: Please select a date");
        dueError.setText("Error: Please select a date");
        
        if(titleField.getText() == null || titleField.getText().equals("")){
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
        
        if(errorStatus == false) {
            status.setText("Task failed to create because of above errors");
            status.setTextFill(Color.web("#ff0000"));
            status.setVisible(true);
            return false;
        }
        else{
            return true;
        }
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
        } else if(doDate.isBefore(now()) || dueDate.isBefore(now())){
            if(doDate.isBefore(now())){
                doError.setText("Error: Do-date must be before current date");
                doError.setVisible(true);
            }
            if(dueDate.isBefore(now())){
                dueError.setText("Error: Due-date must be before current date");
                dueError.setVisible(true);
            }
            return false;
        }
        else {
            return true;
        }
    }
    
}
