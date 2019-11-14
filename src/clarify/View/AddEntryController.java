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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author khang
 */
public class AddEntryController implements Initializable {
    
    PageSwitchHelper p = new PageSwitchHelper();
    Database d = new Database();
    
    @FXML
    private TextArea descArea;
    @FXML
    private Label descError;
    
    @FXML
    private ComboBox startHr;
    @FXML
    private ComboBox startMin;
    @FXML
    private DatePicker startField;
    @FXML
    private Label startError;
    
    @FXML
    private ComboBox endHour;
    @FXML
    private ComboBox endMin;
    @FXML
    private DatePicker endField;
    @FXML
    private Label endError;
    
    @FXML
    private ComboBox taskBox;
    
    @FXML
    private ComboBox categoryBox;
    @FXML
    private Label taskError;
    @FXML
    private Label catError;
    
    @FXML
    private GridPane gridPane;
    @FXML
    private Label success;
    @FXML
    private Label success1;
    @FXML
    private Label fail;
    @FXML
    private Button btn;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadItems();
    }

    public void loadItems() {
                for(int i=0; i<24; i++){
            String hour;
            if(i<10){
                hour = "0"+i;
            }
            else{
                hour = String.valueOf(i);
            }
            startHr.getItems().add(hour);
            endHour.getItems().add(hour);
        }
        
        for(int i=0; i<60; i++){
            String minute;
            if(i<10){
                minute = "0"+i;
            }
            else{
                minute = String.valueOf(i);
            }
            startMin.getItems().add(minute);
            endMin.getItems().add(minute);
        }
        
        try{
            ResultSet rs = d.getResultSet("SELECT task_id, task_title FROM TASKS;");
            while(rs.next()){
                taskBox.getItems().add("TaskID: "+rs.getInt(1)+" | "+rs.getString(2));
            }
        } catch (SQLException e){
            System.out.println("Error: Could not retrieve TaskID");
        }
        
        try{
            ResultSet rs = d.getResultSet("SELECT category_name FROM Categories;");
            while(rs.next()){
                categoryBox.getItems().add(rs.getString(1));
            }
        }
        catch(SQLException e){
            System.out.println("System could not load categories");
        }
    }
    
    public void handleBackButton(ActionEvent event) throws IOException {
        p.changeCenter(event, "TimeLogging.fxml");
    }
    
    public void handleCreateButton(ActionEvent event) {
        System.out.println(checkError());
        if(checkError() == true){
            String startDate = startField.getValue().toString()+" "+startHr.getValue()+":"+startMin.getValue()+":00";
            String endDate = endField.getValue().toString()+" "+endHour.getValue()+":"+endMin.getValue()+":00";
            String desc = descArea.getText();
            
            String catInput = categoryBox.getValue().toString();
            int catId = 0;
            try{
                ResultSet rs = d.getResultSet("SELECT cat_id FROM Categories WHERE category_name = '"+catInput+"';");
                catId = rs.getInt(1);
            }
            catch(SQLException e){
                System.out.println("Could not return SQL result");
            }
            
            String taskInput = taskBox.getValue().toString();
            String taskNo = taskInput.substring(8, taskInput.indexOf('|')-1);           
            int taskId = Integer.parseInt(taskNo);
            
            String st = "INSERT INTO ENTRIES (startTime, endTime, entryDescription, category, task) "
                    + "VALUES('"+startDate+"','"+endDate+"','"+desc+"','"+catId+"','"+taskId+"');";
            
            try{
                d.insertStatement(st);
                gridPane.setVisible(false);
                success.setVisible(true);
                success1.setVisible(true);
                fail.setVisible(false);
                btn.setVisible(false);
            }
            catch(Exception e){
                fail.setVisible(true);
            }
        } else {
            fail.setVisible(true);
        }
    }
    
    public boolean checkError() {
        boolean errorStatus = true;
        
        if(taskBox.getValue() == null){
            errorStatus = false;
            taskError.setVisible(true);
        } else {
            taskError.setVisible(false);
        }       

        if(startHr.getValue() == null || startMin.getValue() == null || startField.getValue() == null){
            errorStatus = false;
            startError.setText("Error: Please select date and time!");
            startError.setVisible(true);
        } else {
            startError.setVisible(false);
            errorStatus = checkDate();
        }

        if(endHour.getValue() == null || endMin.getValue() == null || endField.getValue() == null){
            errorStatus = false;
            endError.setText("Error: Please select date and time!");
            endError.setVisible(true);
        } else {
            endError.setVisible(false);
            errorStatus = checkDate();
        }
        
        if(descArea.getText() == null){
            errorStatus = false;
            descError.setVisible(true);
        } else {
            descError.setVisible(false);
        }
        
        if(categoryBox.getValue() == null){
            errorStatus = false;
            catError.setVisible(true);
        } else {
            catError.setVisible(false);
        } 
        
        return errorStatus;
    }
    
    public boolean checkDate(){
        
        String startString = startField.getValue().toString()+"T"+startHr.getValue()+":"+startMin.getValue()+":00";
        String endString = endField.getValue().toString()+"T"+endHour.getValue()+":"+endMin.getValue()+":00";
        LocalDateTime start = LocalDateTime.parse(startString, ISO_LOCAL_DATE_TIME);
        LocalDateTime end = LocalDateTime.parse(endString, ISO_LOCAL_DATE_TIME);
        if(start.isAfter(end) || start.equals(end)){
            String error = "Error: Start time must be before end time!";
            startError.setText(error);
            startError.setVisible(true);
            endError.setText(error);
            endError.setVisible(true);
            return false;
        }
        else {
            return true;
        }
    }
    
}
