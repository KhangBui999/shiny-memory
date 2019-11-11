import clarify.Util.Database;
import clarify.View.RootLayoutController;
import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
        
    @Override
    public void start(Stage stage) throws Exception {
        loadDatabase();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("clarify/View/RootLayout.fxml"));
        
        Parent root = (Parent) loader.load();
        
        Scene scene = new Scene(root);
        stage.setTitle("Clarify");
        stage.setScene(scene);
        stage.show();
    }
    
    public void loadDatabase() throws SQLException{
        Database.createCategoriesTable();
        Database.createEntriesTable();
        Database.createTaskTable();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
