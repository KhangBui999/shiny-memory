package clarify.Util;
/* DO NOT CHANGE MAKE ANY CHANGES TO THIS CLASS */

import clarify.View.RootLayoutController;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PageSwitchHelper {
    
    public void switcher(ActionEvent event, String page) throws IOException {
        System.out.println("Switching pages");
        Parent parent = FXMLLoader.load(getClass().getResource(page));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    public void switcher(MouseEvent event, String page) throws IOException {
        System.out.println("Switching pages");
        Parent parent = FXMLLoader.load(getClass().getResource(page));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    public void changeCenter(ActionEvent event, String page) throws IOException {
        System.out.println("Switching center of BorderPane");
        BorderPane root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/clarify/View/RootLayout.fxml"));
            root = (BorderPane) loader.load();
            
            RootLayoutController cont = loader.getController();
            
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            cont.setPage(page);
            cont.loadPage(page);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void changeCenter(MouseEvent event, String page) throws IOException {
        System.out.println("Switching center of BorderPane");
        BorderPane root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/clarify/View/RootLayout.fxml"));
            root = (BorderPane) loader.load();
            
            RootLayoutController cont = loader.getController();
            
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            cont.setPage(page);
            cont.loadPage(page);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
