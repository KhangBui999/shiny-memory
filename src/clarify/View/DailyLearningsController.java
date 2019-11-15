/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clarify.View;

import clarify.Util.Database;
import clarify.Util.PageSwitchHelper;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import java.time.LocalDate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class DailyLearningsController implements Initializable{
    
    private Database d = new Database();
    
    @FXML
    private ListView datesList;
    
    @FXML
    private Label date;
    @FXML
    private Label noSelect;
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private ComboBox prevWell;
    @FXML
    private TextArea wellField;
    
    @FXML
    private ComboBox prevBetter;
    @FXML
    private TextArea betterField;
    
    @FXML
    private Label wellError;
    @FXML
    private Label betterError;
    @FXML
    private Label success;
    
    @Override
    public void initialize(URL location, ResourceBundle rb) {
        loadDates();
    }
    
    @FXML
    public void userSelectTask(){
        String date = datesList.getSelectionModel().getSelectedItem().toString();
        this.date.setText(date);
        anchorPane.setVisible(true);
        noSelect.setVisible(false);
        wellError.setVisible(false);
        betterError.setVisible(false);
        success.setVisible(false);
        loadPreviousAnswers();
        getAnswers();
    }
    
    @FXML
    public void handleUpdateButton(){
        if(errorExist() == false){
            String updateDate = reformatToSQLDate(date.getText());
            String st = "SELECT * FROM Reflection WHERE currentDate = '"+updateDate+"';";
            try{
                ResultSet rs = d.getResultSet(st);
                if(rs.next()){
                    updateReflection();
                } else {
                    createReflection();
                }
                success.setVisible(true);
            } catch(SQLException e){

            }
        }
    }
    
    @FXML
    public void createReflection(){
        String createDate = reformatToSQLDate(date.getText());
        String newWell = wellField.getText();
        String newBetter = betterField.getText();
        try {
            String st = "INSERT INTO Reflection (wellAnswer, betterAnswer, currentDate) "
                    + "VALUES('"+newWell+"', '"+newBetter+"', '"+createDate+"');";
            d.insertStatement(st);
            System.out.println(st);
        } catch(Exception e){
            
        }
    }
    
    @FXML
    public void updateReflection(){
        String updateDate = reformatToSQLDate(date.getText());
        String newWell = wellField.getText();
        String newBetter = betterField.getText();
        try{
            String st = "UPDATE Reflection "
                    + "SET wellAnswer = '"+newWell+"',"
                    + "betterAnswer = '"+newBetter+"' "
                    + "WHERE currentDate = '"+updateDate+"';";
            d.insertStatement(st);
            System.out.println(st);
            success.setVisible(true);
        }catch(Exception e){
            //TODO
        }
    }
    
    @FXML
    public boolean errorExist(){
        boolean exist = false;
        
        if(wellField.getText().equals("") || wellField.getText() == ""){
            wellError.setVisible(true);
            exist = true;
        } else {
            wellError.setVisible(false);
        }
        
        if(betterField.getText().equals("") || betterField.getText().equals("")){
            betterError.setVisible(true);
            exist = true;
        } else {
            betterError.setVisible(false);
        }
        
        return exist;
    }
    
    public void getAnswers() {
        String selectedDate = reformatToSQLDate(date.getText());
        try{
            String st = "SELECT * FROM Reflection "
                    + "WHERE currentDate = '"+selectedDate+"';";
            ResultSet rs = d.getResultSet(st);
            if(rs.next()){
                wellField.setText(rs.getString(2));
                betterField.setText(rs.getString(3));
            } else {
                wellField.clear();
                betterField.clear();
            }
            prevWell.setValue(null);
            prevBetter.setValue(null);
        } catch(Exception e){
            
        }
    }
    
    @FXML
    public void loadDates() {
        String earliestDate = getEarliestDate();
        String currentDate = getCurrentDate();
        if(earliestDate == null){
            datesList.getItems().addAll(reformatSQLDate(currentDate));
        } else {
            ArrayList<String> dates = new ArrayList<>();
            String loopedDate = "1";
            int counter = 0;
            while(!loopedDate.equals(reformatSQLDate(currentDate))){
                try{
                    String st = "SELECT MIN(DATE(currentDate, '+"+counter+" day')) FROM Reflection;";
                    ResultSet rs = d.getResultSet(st);
                    loopedDate = reformatSQLDate(rs.getString(1));
                    dates.add(loopedDate);
                
                    counter++;
                } catch(SQLException e){
                    //TODO
                }
            }
            ArrayList<String> finalDates = reverseArrayList(dates);
            ObservableList<String> observableList = FXCollections.observableList(finalDates);
            datesList.setItems(observableList);
        }
    }
    
    public void loadPreviousAnswers(){
        loadWellAnswers();
        loadBetterAnswers();
    }
    
    public String getEarliestDate() {
        try{
            ResultSet rs = d.getResultSet("SELECT MIN(DATE(currentDate)) FROM Reflection");
            return rs.getString(1);
        } catch(SQLException e){
            System.out.println("Could not get earliest date!!!");
            return null;
        }
    }
    
    public String getCurrentDate() {
        try{
            ResultSet rs = d.getResultSet("SELECT DATE('now', 'localtime');");
            return rs.getString(1);
        } catch(SQLException e){
            System.out.println("Could not get current date!!!");
            return null;
        }
    }
    
    public String reformatSQLDate(String date) {
        String day = date.substring(8,10);
        String month = date.substring(5,7);
        String year = date.substring(0,4);
        return day+"-"+month+"-"+year;
    }
    
    public String reformatToSQLDate(String date) {
        String day = date.substring(0, 2);
        String month = date.substring(3, 5);
        String year = date.substring(6, 10);
        return year+"-"+month+"-"+day;
    }
    
    public ArrayList<String> reverseArrayList(ArrayList<String> list){
        ArrayList<String> newList = new ArrayList<>();
        int size = list.size();
        for(int i = size-1; i >= 0; i--){
            newList.add(list.get(i));
        }
        return newList;
    }
    
    @FXML
    public void loadWellAnswers() {
        ArrayList<String> answers = new ArrayList<>();
        try{
            String st = "SELECT DISTINCT wellAnswer FROM Reflection;";
            ResultSet rs = d.getResultSet(st);
            while(rs.next()){
                answers.add(rs.getString(1));
            }
            ObservableList<String> observableList = FXCollections.observableList(answers);
            prevWell.setItems(observableList);
        }catch (SQLException e){
            
        }
    }
    
    @FXML
    public void loadBetterAnswers() {
        ArrayList<String> answers = new ArrayList<>();
        try{
            String st = "SELECT DISTINCT betterAnswer FROM Reflection;";
            ResultSet rs = d.getResultSet(st);
            while(rs.next()){
                answers.add(rs.getString(1));
            }
            ObservableList<String> observableList = FXCollections.observableList(answers);
            prevBetter.setItems(observableList);
        }catch (SQLException e){
            
        }
    }
    
    @FXML
    public void handleSelectWell(){
        try{
            String answer = prevWell.getValue().toString();
            wellField.setText(answer);
        } catch(Exception e){
            System.out.println("Nothing was selected.");
        }
    }
    
    @FXML
    public void handleSelectBetter(){
        try{
            String answer = prevBetter.getValue().toString();
            betterField.setText(answer);
        } catch(Exception e){
            System.out.println("Nothing was selected.");
        }
    }    
    
}

