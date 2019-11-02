 
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
import javafx.scene.chart.PieChart;

/**
 * FXML Controller class
 *
 * @author khang
 */
public class HomeController implements Initializable {

    private static RootLayoutController root;

    @FXML
    private PieChart lifePieChart;

    Database d = new Database();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            // TODO
            loadPieChart();
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void loadPieChart() throws SQLException {
        System.out.println("Loading Pie Chart of My Life");

        ArrayList<String> tasks = new ArrayList<String>();

        Connection conn = DriverManager.getConnection("jdbc:sqlite:INFS2605.db");
        Statement st = conn.createStatement();

        String selectQuery = "SELECT entryDescription FROM ENTRIES WHERE ent_id IS NOT NULL;";
        ResultSet rs = st.executeQuery(selectQuery);

        while (rs.next()) {
            tasks.add(rs.getString(1));
        }

        System.out.println(tasks);

        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        //  new PieChart.Data("ta", 13),
                        new PieChart.Data(tasks.get(0), 25),
                        new PieChart.Data(tasks.get(1), 10));

        lifePieChart.setData(pieChartData);
        lifePieChart.setStartAngle(90);

        st.close();
        conn.close();
    }

}
