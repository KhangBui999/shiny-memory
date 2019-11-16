/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clarify.View;

import clarify.Model.Task;
import clarify.Util.Database;
import clarify.Util.PageSwitchHelper;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
public class UpdateTaskController implements Initializable {
    
    private int id;
    private Task task;
    private final PageSwitchHelper p = new PageSwitchHelper();
    private final Database d = new Database();
    
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
    private ComboBox compStatus;
    
    @FXML
    private Label status;
    
    @FXML
    private GridPane gridPane;
    
    @FXML
    private Button btn;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public Task getTask() {
        return this.task;
    }
    
    public void setTask(Task task) {
        this.task = task;
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        priorityField.textProperty().bind(prioritySlider.valueProperty().asString());
        compStatus.getItems().addAll("Not Started", "Doing", "Done");
        //Don't touch this lmao
    }    
    
    public void retrieveTaskFromDb() throws SQLException {
        String retrieve = "SELECT * FROM TASKS "
                + "WHERE task_id = '"+getId()+"';";
        ResultSet rs = d.getResultSet(retrieve);
        while(rs.next()) {
            setTask(new Task(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7)));
        }
        System.out.println(task);
        changeFields();
    }
    
    public void changeFields() {
        this.titleField.setText(this.task.getTitleProperty().getValue());
        this.descArea.setText(this.task.getDescriptionProperty().getValue());
        this.doField.setValue(LocalDate.parse(task.getString(task.getDoDateProperty())));
        this.dueField.setValue(LocalDate.parse(task.getString(task.getDueDateProperty())));
        this.prioritySlider.setValue(this.task.getInt(this.task.getPriorityProperty()));
        this.compStatus.setValue(this.compStatus.getItems().get(this.task.getInt(this.task.getCompleteProperty())));
    }
    
    public void handleUpdateButton(ActionEvent event) {
        if(checkError() == true){
            String title = titleField.getText();
            String desc = descArea.getText();
            String doDate = doField.getValue().toString();
            String dueDate = dueField.getValue().toString();
            int priority = (int)prioritySlider.getValue();
            int compOrNot = convertValue(compStatus.getValue().toString());
            try {
                String insert = "UPDATE TASKS "
                        + "SET task_title = '"+title+"', "
                        + "task_desc = '"+desc+"', "
                        + "do_date = '"+doDate+"', "
                        + "due_date = '"+dueDate+"',"
                        + "priority = '"+priority+"',"
                        + "status = '"+compOrNot+"' "
                        + "WHERE task_id = "+getId()+";";
                d.insertStatement(insert);
                System.out.println("SQL statement was updated successfully");
                this.gridPane.setVisible(false);
                this.btn.setVisible(false);
                this.status.setText("Task was updated successfully. Click 'BACK' to go to previous page.");
                this.status.setTextFill(Color.web("#00e500"));
                this.status.setVisible(true);
            }
            catch (SQLException e) {
                this.status.setText("Task failed to updated due to a system SQL error. Please try again.");
                this.status.setTextFill(Color.web("#ff0000"));
                this.status.setVisible(true);
                System.out.println("SQL statement could not be updated successfully");
            }
        }
    }
    
    public int convertValue(String value) {
        switch (value) {
            case "Not Started":
                return 0;
            case "Doing":
                return 1;
            case "Done":
                return 2;
            default:
                return 0;
        }
    }
    
    public boolean checkError(){
        boolean errorStatus = true;
        
        if(titleField.getText() == null || titleField.getText().equals("")){
            titleError.setVisible(true);
            errorStatus = false;
        } else {
            titleError.setVisible(false);
        }
        
        if(errorStatus == true) {
            errorStatus = dateCheck();
        }
        
        return errorStatus;
    }
    
    public boolean dateCheck() {
        LocalDate doDate = doField.getValue();
        LocalDate dueDate = dueField.getValue();
        
        if(doDate.isAfter(dueDate)){
            doError.setText("Error: Do-date must be BEFORE due-date");
            dueError.setText("Error: Do-date must be BEFORE due-date");
            doError.setVisible(true);
            dueError.setVisible(true);
            return false;
        }
        else{
            return true;
        }
    }
    
    public void handleBackButtonK(ActionEvent event) throws IOException {
        p.changeCenter(event, "/clarify/View/KanbanBoard.fxml");
    }
    
    public void handleBackButtonD(ActionEvent event) throws IOException {
        p.changeCenter(event, "/clarify/View/DeepFocus.fxml");
    }
    
}