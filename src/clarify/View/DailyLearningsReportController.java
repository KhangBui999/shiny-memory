/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clarify.View;

import clarify.Model.Reflection;
import clarify.Util.Database;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author khang
 */
public class DailyLearningsReportController implements Initializable {

    Database d = new Database();
    
    @FXML
    private TableView<Reflection> wellTable;
    @FXML
    private TableColumn<Reflection, String> wellAnswerColumn;
    @FXML
    private TableColumn<Reflection, String> wellOccurencesColumn;
    
    @FXML
    private TableView<Reflection> betterTable;
    @FXML
    private TableColumn<Reflection, String> betterAnswerColumn;
    @FXML
    private TableColumn<Reflection, String> betterOccurencesColumn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        wellAnswerColumn.setCellValueFactory(
                cellData -> cellData.getValue().getAnswer());
        wellOccurencesColumn.setCellValueFactory(
                cellData -> cellData.getValue().getFrequency());
        
        wellTable.setItems(getWellAnswers());
        
        
        betterAnswerColumn.setCellValueFactory(
                cellData -> cellData.getValue().getAnswer());
        betterOccurencesColumn.setCellValueFactory(
                cellData -> cellData.getValue().getFrequency());
        
        betterTable.setItems(getBetterAnswers());
        
    }

    public ObservableList<Reflection> getBetterAnswers() {
        ArrayList<Reflection> answers = new ArrayList<>();
        try{
            String query = "SELECT DISTINCT betterAnswer, COUNT(*) " +
                    "FROM Reflection " +
                    "WHERE currentDate > DATE('now', '-30 day', 'localtime') " +
                    "AND currentDate < DATE('now', '+1 day', 'localtime') " +
                    "GROUP BY betterAnswer " +
                    "ORDER BY COUNT(*) DESC;";
            ResultSet rs = d.getResultSet(query);
            while(rs.next()){
                answers.add(new Reflection(rs.getString(1), rs.getString(2)));
            }
        } catch(Exception e){
            //DO NOTHING
        }
        ObservableList<Reflection> reflection = FXCollections.observableArrayList(answers);
        return reflection;
    }
    
    public ObservableList<Reflection> getWellAnswers() {
        ArrayList<Reflection> answers = new ArrayList<>();
        try{
            String query = "SELECT DISTINCT wellAnswer, COUNT(*) " +
                    "FROM Reflection " +
                    "WHERE currentDate > DATE('now', '-30 day', 'localtime') " +
                    "AND currentDate < DATE('now', '+1 day', 'localtime') " +
                    "GROUP BY wellAnswer " +
                    "ORDER BY COUNT(*) DESC;";
            ResultSet rs = d.getResultSet(query);
            while(rs.next()){
                answers.add(new Reflection(rs.getString(1), rs.getString(2)));
            }
        } catch(Exception e){
            //DO NOTHING
        }
        ObservableList<Reflection> reflection = FXCollections.observableArrayList(answers);
        return reflection;
    }
    
}
