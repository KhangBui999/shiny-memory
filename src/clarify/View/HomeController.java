package clarify.View;

import clarify.Util.Database;
import clarify.Util.PageSwitchHelper;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

/**
 * FXML Controller class
 *
 * @author khang
 */
public class HomeController implements Initializable {

    private static RootLayoutController root;

    @FXML
    private PieChart lifePieChart;

    @FXML
    private ArrayList<String> category;

    @FXML
    private ArrayList<Integer> duration;

    @FXML
    private BarChart<String, Integer> dailyBarChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private BarChart<String, Integer> weeklyBarChart;

    @FXML
    private CategoryAxis xWeekly;

    @FXML
    private NumberAxis yWeekly;

    @FXML
    private CategoryAxis xTrends;

    @FXML
    private NumberAxis yTrends;

    @FXML
    private LineChart<String, Integer> trendsLineChart;

    Database d = new Database();
    PageSwitchHelper p = new PageSwitchHelper();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            loadPieChart();
            loadDailyBarChart();
            loadWeeklyBarChart();
            loadLineChart();
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void loadPieChart() throws SQLException {
        System.out.println("Loading Pie Chart of My Life");

        ArrayList<String> entriesList = new ArrayList<>();

        Connection conn = DriverManager.getConnection("jdbc:sqlite:INFS2605.db");
        Statement st = conn.createStatement();

//        String selectQuery = "SELECT entryDescription FROM ENTRIES WHERE ent_id IS NOT NULL;";
//        ResultSet rs1 = st.executeQuery(selectQuery);
//
//        while (rs1.next()) {
//            entriesList.add(rs1.getString(1));
//        }
//        System.out.println("Activities entered: " + entriesList);
//        ArrayList<String> durations = new ArrayList<>();
        String selectQuery = "SELECT entryDescription, ((strftime('%s',endTime) - strftime('%s',startTime))/60)FROM ENTRIES WHERE ent_id IS NOT NULL;";
        ResultSet rs = st.executeQuery(selectQuery);

        ObservableList<PieChart.Data> lifePieChartData
                = FXCollections.observableArrayList();

        while (rs.next()) {
            PieChart.Data pieChartData = new PieChart.Data(rs.getString(1), rs.getInt(2));

            lifePieChartData.add(pieChartData);

            lifePieChart.setData(lifePieChartData);

        }
//
//        ArrayList<Integer> durationsInt = new ArrayList<Integer>(durations.size());
//        for (String myInt : durations) {
//            durationsInt.add(Integer.valueOf(myInt));
//        }
//        System.out.println("Time spent on each activity (minutes): " + durationsInt);

    }

    public void loadDailyBarChart() throws SQLException {
        System.out.println("Loading Daily Breakdown Bar Chart");

        Connection conn = DriverManager.getConnection("jdbc:sqlite:INFS2605.db");
        Statement st = conn.createStatement();

        //TOP 5 ENTRY NAMES BY TOTAL HOURS
        ArrayList<String> top5entriesList = new ArrayList<>();

        String selectQuery = "SELECT entryDescription FROM ENTRIES ORDER BY ((strftime('%s',endTime) - strftime('%s',startTime))/60) DESC LIMIT 5;";
        ResultSet rs3 = st.executeQuery(selectQuery);

        while (rs3.next()) {
            top5entriesList.add(rs3.getString(1));
        }
        //DAYS ELAPSED TOTAL
        ArrayList<Integer> daysElapsed = new ArrayList<>();

        String selectQuery3 = "SELECT round(MAX(julianday(endtime)-julianday(startTime))+0.5)FROM ENTRIES;";
        ResultSet rs4 = st.executeQuery(selectQuery3);

        while (rs4.next()) {
            daysElapsed.add(rs4.getInt(1));
        }

        //TOP 5 HOURS TOTAL
        ArrayList<Integer> top5hours = new ArrayList<>();

        String selectQuery4 = "SELECT ((strftime('%s',endTime) - strftime('%s',startTime))/3600) entryDescription FROM ENTRIES ORDER BY ((strftime('%s',endTime) - strftime('%s',startTime))/60) DESC LIMIT 5;";
        ResultSet rs5 = st.executeQuery(selectQuery4);

        while (rs5.next()) {
            top5hours.add(rs5.getInt(1));
        }

        //AVERAGE HOURS PER DAY FOR TOP 5 ENTRIES
        ArrayList<Integer> top5perDay = new ArrayList<>();

        for (int f = 0; f < top5hours.size(); f++) {
            top5perDay.add(top5hours.get(f) / daysElapsed.get(0));
        }
        System.out.println(top5perDay);

        //ADD TO BAR CHART
        XYChart.Series set1 = new XYChart.Series<>();

        for (int l = 0; l < 5; l++) {
            set1.getData().add(new XYChart.Data(top5entriesList.get(l), top5perDay.get(l)));
        }

        dailyBarChart.getData().addAll(set1);

        st.close();
        conn.close();
    }

    public void loadWeeklyBarChart() throws SQLException {

        System.out.println("Loading Daily Breakdown Bar Chart");

        Connection conn = DriverManager.getConnection("jdbc:sqlite:INFS2605.db");
        Statement st = conn.createStatement();

        //TOP 5 ENTRY NAMES BY TOTAL HOURS
        ArrayList<String> top5entriesList = new ArrayList<>();

        String selectQuery = "SELECT entryDescription FROM ENTRIES ORDER BY ((strftime('%s',endTime) - strftime('%s',startTime))/60) DESC LIMIT 5;";
        ResultSet rs3 = st.executeQuery(selectQuery);

        while (rs3.next()) {
            top5entriesList.add(rs3.getString(1));
        }
        //DAYS ELAPSED TOTAL
        ArrayList<Integer> daysElapsed = new ArrayList<>();

        String selectQuery3 = "SELECT round(MAX(julianday(endtime)-julianday(startTime))+6.5)/7 FROM ENTRIES;";

        ResultSet rs4 = st.executeQuery(selectQuery3);

        while (rs4.next()) {
            daysElapsed.add(rs4.getInt(1));
        }

        //TOP 5 HOURS TOTAL
        ArrayList<Integer> top5hours = new ArrayList<>();

        String selectQuery4 = "SELECT ((strftime('%s',endTime) - strftime('%s',startTime))/(3600*7)) entryDescription FROM ENTRIES ORDER BY ((strftime('%s',endTime) - strftime('%s',startTime))/60) DESC LIMIT 5;";
        ResultSet rs5 = st.executeQuery(selectQuery4);

        while (rs5.next()) {
            top5hours.add(rs5.getInt(1));
        }

        //AVERAGE HOURS PER DAY FOR TOP 5 ENTRIES
        ArrayList<Integer> top5perDay = new ArrayList<>();

        for (int f = 0; f < top5hours.size(); f++) {
            top5perDay.add(top5hours.get(f) / daysElapsed.get(0));
        }
        System.out.println(top5perDay);

        //ADD TO BAR CHART
        XYChart.Series set1 = new XYChart.Series<>();

        for (int l = 0; l < 5; l++) {
            set1.getData().add(new XYChart.Data(top5entriesList.get(l), top5perDay.get(l)));
        }

        weeklyBarChart.getData().addAll(set1);

        st.close();
        conn.close();

    }

    public void loadLineChart() {

        XYChart.Series series1 = new XYChart.Series(); //Make a new XYChart object
//Add Data
        series1.getData().add(new XYChart.Data("1", 23));
        series1.getData().add(new XYChart.Data("2", 14));
        series1.getData().add(new XYChart.Data("3", 15));

        trendsLineChart.getData().addAll(series1);

    }

    public void handleAddEntryBtn(ActionEvent event) throws IOException {
        p.changeCenter(event, "/clarify/View/AddEntry.fxml");
    }

}
