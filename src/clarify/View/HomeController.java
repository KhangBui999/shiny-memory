package clarify.View;

import clarify.Util.Database;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
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
    private BarChart<?, ?> dailyBarChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    Database d = new Database();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            loadPieChart();
            loadDailyBarChart();
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void loadPieChart() throws SQLException {
        System.out.println("Loading Pie Chart of My Life");

        ArrayList<String> entriesList = new ArrayList<>();

        Connection conn = DriverManager.getConnection("jdbc:sqlite:INFS2605.db");
        Statement st = conn.createStatement();

        String selectQuery = "SELECT entryDescription FROM ENTRIES WHERE ent_id IS NOT NULL;";
        ResultSet rs1 = st.executeQuery(selectQuery);

        while (rs1.next()) {
            entriesList.add(rs1.getString(1));
        }

        System.out.println("Activities entered: " + entriesList);

        ArrayList<String> durations = new ArrayList<>();

        String selectQuerys = "SELECT ((strftime('%s',endTime) - strftime('%s',startTime))/60)FROM ENTRIES WHERE ent_id IS NOT NULL;";
        ResultSet rs2 = st.executeQuery(selectQuerys);
        while (rs2.next()) {
            durations.add(rs2.getString(1));
        }

        //System.out.println(durations);
        ArrayList<Integer> durationsInt = new ArrayList<Integer>(durations.size());
        for (String myInt : durations) {
            durationsInt.add(Integer.valueOf(myInt));
        }
        System.out.println("Time spent on each activity (minutes): " + durationsInt);

        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data(entriesList.get(0), durationsInt.get(0)),
                        new PieChart.Data(entriesList.get(1), durationsInt.get(1)));

        lifePieChart.setData(pieChartData);
        lifePieChart.setStartAngle(90);

        st.close();
        conn.close();
    }

    public void loadDailyBarChart() {
        XYChart.Series set1 = new XYChart.Series<>();
        set1.getData().add(new XYChart.Data("Dancing", 5));
        set1.getData().add(new XYChart.Data("Singing", 2));
        set1.getData().add(new XYChart.Data("Mooing", 10));

        dailyBarChart.getData().addAll(set1);

    }

}
