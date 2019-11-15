/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clarify.View;

import clarify.Model.Task;
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
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

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
    @FXML
    private ListView selectedTaskList;
    @FXML
    private ArrayList<String> selectedTasks = new ArrayList<String>();

    @FXML
    private ObservableList<XYChart.Series> taskSeries;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rawDate = getDates();
        ArrayList<String> dateDisplay = new ArrayList<String>();
        for (String date : rawDate) {
            dateDisplay.add(date);
        }
        dateMarkers = FXCollections.observableArrayList(dateDisplay);
        CategoryAxis xAxis = (CategoryAxis) lineChart.getXAxis();
        xAxis.setCategories(dateMarkers);
        loadTaskList();
    }

    public ArrayList<String> getDates() {
        ArrayList<String> dates = new ArrayList<String>();
        try {
            for (int i = 6; i > 0; i--) {
                String st = "SELECT date('now', '-" + 7 * (i - 1) + " day', 'localtime');";
                ResultSet rs = d.getResultSet(st);
                String input = rs.getString(1).substring(8, 10) + "/" + rs.getString(1).substring(5, 7);
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
        try {
            ResultSet rs = d.getResultSet(st);
            while (rs.next()) {
                taskList.getItems().add(rs.getString(1) + " | " + rs.getString(2));
            }
        } catch (SQLException e) {
            //TODO
        }
        //taskList.getItems.add();
    }

    public void handleAddTask() {
        String selected = taskList.getValue().toString();
        boolean exist = selectedTasks.contains(selected);
        if (exist == false) {
            selectedTasks.add(selected);
            ObservableList<String> selectList = FXCollections.observableArrayList(selectedTasks);
            selectedTaskList.setItems(selectList);
            loadLines();
        } else {
            //TODO print out cannot add an exisiting task or you have already added
        }
    }

    public void handleRemoveTask() {
        String task = selectedTaskList.getSelectionModel().getSelectedItem().toString();
        selectedTasks.remove(task);
        ObservableList<String> selectList = FXCollections.observableArrayList(selectedTasks);
        selectedTaskList.setItems(selectList);
        loadLines();
    }

    public void loadLines() {
        lineChart.getData().clear();
        int[] total = new int[6];
        for (int i = 0; i < 6; i++) {
            total[i] = 0;
        }

        try {
            for (String task : selectedTasks) {
                int taskNo = Integer.parseInt(task.substring(0, task.indexOf('|') - 1));
                for (int i = 6; i > 0; i--) {
                    String st = "SELECT SUM((strftime('%s',endTime) - strftime('%s',startTime))/60) FROM ENTRIES "
                            + "WHERE task = '" + taskNo + "' "
                            + "AND endTime > date('now', '-" + (7 * i) + " day', 'localtime') "
                            + "AND endTime < date('now', '-" + 7 * (i - 1) + " day', 'localtime');";
                    ResultSet rs = d.getResultSet(st);
                    total[i - 1] += rs.getInt(1);
                }
            }

            for (String task : selectedTasks) {
                String taskName = task.substring(task.indexOf('|') + 2);
                int taskNo = Integer.parseInt(task.substring(0, task.indexOf('|') - 1));
                XYChart.Series series = new XYChart.Series();
                series.setName(taskName);
                for (int i = 6; i > 0; i--) {
                    String st1 = "SELECT date('now', '-" + 7 * (i - 1) + " day', 'localtime');";
                    ResultSet rs1 = d.getResultSet(st1);
                    String input = rs1.getString(1).substring(8, 10) + "/" + rs1.getString(1).substring(5, 7);
                    String st2 = "SELECT SUM((strftime('%s',endTime) - strftime('%s',startTime))/60) FROM ENTRIES "
                            + "WHERE task = '" + taskNo + "' "
                            + "AND endTime > date('now', '-" + (7 * i) + " day', 'localtime') "
                            + "AND endTime < date('now', '-" + 7 * (i - 1) + " day', 'localtime');";
                    ResultSet rs2 = d.getResultSet(st2);

                    double n = rs2.getInt(1);
                    double d = total[i - 1];
                    double value;
                    System.out.println(n + "/" + d);
                    if (d != 0) {
                        value = (n / d) * 100;
                    } else {
                        value = 0;
                    }

                    series.getData().add(new XYChart.Data(input, value));
                }
                lineChart.getData().add(series);
            }
        } catch (SQLException e) {
            //TODO
        }
    }
//NOTE: Percentages of selected task!    

}
//SQL CODE
//SELECT SUM((strftime('%s',endTime) - strftime('%s',startTime))/60) FROM ENTRIES
//WHERE task = 2
//AND endTime > date('now', '-14 day', 'localtime')
//AND endTime < date('now', '-7 day', 'localtime');
