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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * BIG SHOUT OUT TO BLAIR
 * FOR GIVING US THE IDEA OF DRAG AND DROP
 * VIA ARROWS
 * - K
 */

public class KanbanBoardController{

    PageSwitchHelper p = new PageSwitchHelper();
    Database d = new Database();

    @FXML
    private ListView<Task> todayList;
    private ObservableList<Task> obsToday;
    
    @FXML
    private ListView<Task> tmrList;
    private ObservableList<Task> obsTmr;
    
    @FXML
    private ListView<Task> weekList;
    private ObservableList<Task> obsWeek;
    
    @FXML
    private ListView<Task> compList;
    private ObservableList<Task> obsComp;
    
    public void initialize() {
        loadTask();
    }
    
    @FXML
    public void loadTask(){
        this.obsToday = FXCollections.observableArrayList(getTodayTask());
        todayList.setItems(this.obsToday);
        todayList.setCellFactory(task -> new TaskNoteController());
               
        this.obsTmr = FXCollections.observableArrayList(getTomorrowTask());
        tmrList.setItems(this.obsTmr);
        tmrList.setCellFactory(task -> new TaskNoteController());
        
        this.obsWeek = FXCollections.observableArrayList(getWeeklyTask());
        weekList.setItems(this.obsWeek);
        weekList.setCellFactory(task -> new TaskNoteController());
        
        this.obsComp = FXCollections.observableArrayList(getCompTask());
        tmrList.getItems().clear();
        compList.setItems(this.obsComp);
        compList.setCellFactory(task -> new TaskNoteController());
    }
    
    @FXML
    public void deselectWhenComp(){
        todayList.getSelectionModel().clearSelection();
        tmrList.getSelectionModel().clearSelection();
        weekList.getSelectionModel().clearSelection();
    }
    
    @FXML
    public void deselectWhenToday(){
        compList.getSelectionModel().clearSelection();
        tmrList.getSelectionModel().clearSelection();
        weekList.getSelectionModel().clearSelection();
    }
    
    @FXML
    public void deselectWhenTmr(){
        compList.getSelectionModel().clearSelection();
        todayList.getSelectionModel().clearSelection();
        weekList.getSelectionModel().clearSelection();
    }
    
    @FXML
    public void deselectWhenWeek(){
        compList.getSelectionModel().clearSelection();
        todayList.getSelectionModel().clearSelection();
        tmrList.getSelectionModel().clearSelection();
    }
    
    @FXML
    public void compToToday() {
        try{
            Task task = compList.getSelectionModel().getSelectedItem();
            String st = "UPDATE TASKS SET status = '0' WHERE task_id = '"+task.getInt(task.getTaskId())+"';";
            d.insertStatement(st);
        } catch(Exception e){
            //TODO
        }
    }
    
    @FXML
    public void todayToComp() {
        try{
            Task task = todayList.getSelectionModel().getSelectedItem();
            String st = "UPDATE TASKS SET status = '2' WHERE task_id = '"+task.getInt(task.getTaskId())+"';";
            d.insertStatement(st);
        } catch(Exception e){
            //TODO
        }
    }
    
    @FXML
    public void todayToTmr() {
        try{
            Task task = todayList.getSelectionModel().getSelectedItem();
            String st = "UPDATE TASKS SET due_date = DATE('now', '+1 day', 'localtime') WHERE task_id = '"+task.getInt(task.getTaskId())+"';";
            d.insertStatement(st);
            loadTask();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @FXML
    public void tmrToToday() {
        try{
            Task task = tmrList.getSelectionModel().getSelectedItem();
            String st = "UPDATE TASKS SET due_date = DATE('now', 'localtime') WHERE task_id = '"+task.getInt(task.getTaskId())+"';";
            d.insertStatement(st);
            loadTask();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @FXML
    public void tmrToWeek() {
        try{
            Task task = tmrList.getSelectionModel().getSelectedItem();
            String st = "UPDATE TASKS SET due_date = DATE('now', '+2 day', 'localtime') WHERE task_id = '"+task.getInt(task.getTaskId())+"';";
            d.insertStatement(st);
            loadTask();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @FXML
    public void weekToTmr() {
        try{
            Task task = weekList.getSelectionModel().getSelectedItem();
            String st = "UPDATE TASKS SET due_date = DATE('now', '+1 day', 'localtime') WHERE task_id = '"+task.getInt(task.getTaskId())+"';";
            d.insertStatement(st);
            loadTask();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public ArrayList<Task> getTodayTask(){
        System.out.println("Today ListView loaded");
        ArrayList<Task> task = new ArrayList<>();
        try {
            ResultSet rs = d.getResultSet("SELECT * FROM TASKS"
                    + " WHERE DATE(due_date) = DATE('now', 'localtime')"
                    + " AND status < 2;");
            while (rs.next()){
                task.add(new Task(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7)));
            }
        }
        catch (SQLException e){
            System.out.println("SQL error");
        }
        return task;
    }
    
    public ArrayList<Task> getTomorrowTask(){
        System.out.println("Tomorrow ListView loaded");
        ArrayList<Task> task = new ArrayList<>();
        try {
            ResultSet rs = d.getResultSet("SELECT * FROM TASKS"
                    + " WHERE DATE(due_date) = DATE('now', '+1 day', 'localtime')"
                    + " AND status < 2;");
            while (rs.next()){
                task.add(new Task(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7)));  
            }
        }
        catch (SQLException e){
            System.out.println("SQL error");
        }
        return task;
    }
    
    public ArrayList<Task> getWeeklyTask(){
        System.out.println("Weekly ListView loaded");
        ArrayList<Task> task = new ArrayList<>();
        try {
            ResultSet rs = d.getResultSet("SELECT * FROM TASKS"
                    + " WHERE DATE(due_date) > DATE('now', '+1 day', 'localtime')"
                    + " AND DATE(due_date) < DATE('now', '+7 day', 'localtime')"
                    + " AND status < 2;");
            while (rs.next()){
                task.add(new Task(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7)));  
            }
        }
        catch (SQLException e){
            System.out.println("SQL error");
        }
        return task;
    }

    public ArrayList<Task> getCompTask(){
        System.out.println("Completed ListView loaded");
        ArrayList<Task> task = new ArrayList<>();
        try {
            ResultSet rs = d.getResultSet("SELECT * FROM TASKS"
                    + " WHERE DATE(due_date) = DATE('now', 'localtime')"
                    + " AND status = 2;");
            while (rs.next()){
                task.add(new Task(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7)));
            }
        }
        catch (SQLException e){
            System.out.println("SQL error");
        }
        return task;
    }
    
    public void handleAddTaskBtn(ActionEvent event) throws IOException {
        p.changeCenter(event, "/clarify/View/AddTask.fxml");
    }

}

//SELECT * FROM TASKS
//WHERE DATE(due_date) = DATE('now', '+1 day');