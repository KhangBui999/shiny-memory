/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clarify.View;

import clarify.Util.Database;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author khang
 */
public class TrendsController implements Initializable {

    Database d = new Database();
    
    @FXML
    private LineChart lineChart;
    
    private ArrayList<String> rawDate;
    @FXML
    private ObservableList<String> dateMarkers;
    @FXML
    private ComboBox taskList;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rawDate = getDates();
        ArrayList<String> dateDisplay = new ArrayList<String>();
        for(String date : rawDate){
            dateDisplay.add(date);
            System.out.println(date);
        }
        dateMarkers = FXCollections.observableArrayList(dateDisplay);
        CategoryAxis xAxis = (CategoryAxis) lineChart.getXAxis();
        xAxis.setCategories(dateMarkers);
        loadTaskList();
    }
    
    public ArrayList<String> getDates() {
        System.out.println("Milestone 1: ACTIVATED!");
        ArrayList<String> dates = new ArrayList<String>();
        try{
            System.out.println("Milestone 2: ACTIVATED!");
            for(int i=9; i>0; i--){
                System.out.println("Milestone 3: ACTIVATED!");
                String st = "SELECT date('now', '-"+7*(i-1)+" day', 'localtime');";
                System.out.println(st);
                ResultSet rs = d.getResultSet(st);
                System.out.println(rs.getString(1));
                String input = rs.getString(1).substring(8, 10)+"/"+rs.getString(1).substring(5, 7);
                dates.add(input);
            }
        } catch (SQLException e) {
            System.out.println("System failed to load dates benchmark");
        } finally {
            return dates;
        }
    }
    
    public void loadTaskList() {
        String st = "SELECT task_id, task_title FROM TASKS;";
        try{
           ResultSet rs = d.getResultSet(st);
           while(rs.next()){
               taskList.getItems().add(rs.getString(1)+" | "+rs.getString(2));
           }
        } catch (SQLException e){
            //TODO
        }
        //taskList.getItems.add();
    }
//NOTE: Percentages of selected task!    
    
}
