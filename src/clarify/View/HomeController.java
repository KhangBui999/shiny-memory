package clarify.View;

import clarify.Util.Database;
import clarify.Util.PageSwitchHelper;
import com.sun.javafx.charts.Legend;
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
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

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
    private BarChart<String, Integer> dailyBarChart;

    @FXML
    private Button logTime;

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

    Database d = new Database();
    private final PageSwitchHelper p = new PageSwitchHelper();
    
    @FXML
    public void addLogPressed(ActionEvent event) throws IOException{
        p.changeCenter(event, "TimeLogging.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            loadPieChart();
            loadDailyBarChart();
            loadWeeklyBarChart();
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Loads Pie Chart
    public void loadPieChart() throws SQLException {
        System.out.println("Loading Pie Chart of My Life");

        ArrayList<String> categoryList = new ArrayList<>();
        ArrayList<String> colour = new ArrayList<>();

        Connection conn = DriverManager.getConnection("jdbc:sqlite:INFS2605.db");
        Statement st = conn.createStatement();

        String selectQuery = "SELECT DISTINCT c.category_name, c.colour FROM Categories c, ENTRIES WHERE c.cat_id = ENTRIES.category;";
        ResultSet rs1 = st.executeQuery(selectQuery);

        while (rs1.next()) {
            categoryList.add(rs1.getString(1));
            colour.add(rs1.getString(2));
        }

        System.out.println("Activities entered: " + categoryList);

        String st2 = "SELECT SUM((strftime('%s',endTime) - strftime('%s',startTime))/60) "
                + "FROM ENTRIES e, Categories c "
                + "WHERE c.cat_id = e.category;";
        ResultSet rs2 = d.getResultSet(st2);
        int remaining = rs2.getInt(1);

        ArrayList<Integer> durations = new ArrayList<>();

        for (String category : categoryList) {
            String st3 = "SELECT SUM((strftime('%s',endTime) - strftime('%s',startTime))/60) "
                    + "FROM ENTRIES e, Categories c "
                    + "WHERE c.category_name = '" + category + "' "
                    + "AND c.cat_id = e.category;";
            ResultSet rs = d.getResultSet(st3);
            durations.add(Integer.valueOf(rs.getString(1)));
        }

        System.out.println("Time spent on each activity (minutes): " + durations);

        ArrayList<PieChart.Data> categoryData = new ArrayList<>();

        for (int i = 0; i < categoryList.size(); i++) {
            if (durations.get(i) > 0) {
                categoryData.add(new PieChart.Data(categoryList.get(i), durations.get(i)));
            } else {
                //TODO
            }
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(categoryData);

        lifePieChart.setData(pieChartData);
        lifePieChart.setStartAngle(90);

        int counter = 0;

        //Pie chart colours
        //Adapted from: https://github.com/blairw/emona/blob/master/ChartsDemo/src/chartsdemo/FXMLDocumentController.java
        for (PieChart.Data data : pieChartData) {
            data.getNode().setStyle("-fx-pie-color: " + colour.get(counter) + ";");
            counter++;
        }

        counter = 0;
        for (Node n : lifePieChart.getChildrenUnmodifiable()) {
            if (n instanceof Legend) {
                Legend l = (Legend) n;
                for (Legend.LegendItem li : l.getItems()) {
                    Node thisNode = li.getSymbol();
                    thisNode.setStyle(
                            "-fx-pie-color: " + colour.get(counter) + ";"
                    );
                    counter++;
                }
            }
        }

        st.close();
        conn.close();
    }

    //Loads Daily Breakdown
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
        dailyBarChart.setLegendVisible(false);
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

}
