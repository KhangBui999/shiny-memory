package clarify.Util;
/* DO NOT CHANGE MAKE ANY CHANGES TO THIS CLASS */

import clarify.Model.Task;
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
    
    //From: Blair's PageSwitchHelper.java (Week 4-5 Tutorial)
    public void switcher(ActionEvent event, String page) throws IOException {
        System.out.println("Switching pages");
        Parent parent = FXMLLoader.load(getClass().getResource(page));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    //From: Blair's PageSwitchHelper.java (Week 4-5 Tutorial)
    public void switcher(MouseEvent event, String page) throws IOException {
        System.out.println("Switching pages");
        Parent parent = FXMLLoader.load(getClass().getResource(page));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    //This method changes the center of the RootLayout
    //Adapted from: https://stackoverflow.com/questions/40108443/how-to-change-centerpane-from-leftpane-in-javafx-borderpane
    //AND Blair's PageSwitchHelper.java file from Week 4-5 Tutorial
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

            cont.loadPage(page);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeToUpdateTask(ActionEvent event, int id) throws IOException {
        System.out.println("Switching center of BorderPane to UpdateTask");
        BorderPane root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/clarify/View/RootLayout.fxml"));
            root = (BorderPane) loader.load();
            
            RootLayoutController cont = loader.getController();
            
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(true);
            stage.show();

            cont.loadTaskUpdate(id);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void changeToDeleteTask(ActionEvent event, int id) throws IOException {
        System.out.println("Switching center of BorderPane to AddTask");
        BorderPane root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/clarify/View/RootLayout.fxml"));
            root = (BorderPane) loader.load();
            
            RootLayoutController cont = loader.getController();
            
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(true);
            stage.show();

            cont.loadTaskDelete(id);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

   public void changeToUpdateCategory(ActionEvent event, int id) throws IOException {
        System.out.println("Switching center of BorderPane to AddTask");
        BorderPane root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/clarify/View/RootLayout.fxml"));
            root = (BorderPane) loader.load();
            
            RootLayoutController cont = loader.getController();
            
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(true);
            stage.show();

            cont.loadCategoryUpdate(id);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    public void changeToDeleteCategory(ActionEvent event, int id) throws IOException {
        System.out.println("Switching center of BorderPane to AddTask");
        BorderPane root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/clarify/View/RootLayout.fxml"));
            root = (BorderPane) loader.load();
            
            RootLayoutController cont = loader.getController();
            
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(true);
            stage.show();

            cont.loadCategoryDelete(id);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    public void changeToUpdateEntry(ActionEvent event, int id) throws IOException {
        System.out.println("Switching center of BorderPane to AddTask");
        BorderPane root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/clarify/View/RootLayout.fxml"));
            root = (BorderPane) loader.load();
            
            RootLayoutController cont = loader.getController();
            
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(true);
            stage.show();

            cont.loadEntryUpdate(id);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void changeToDeleteEntry(ActionEvent event, int id) throws IOException {
        System.out.println("Switching center of BorderPane to AddTask");
        BorderPane root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/clarify/View/RootLayout.fxml"));
            root = (BorderPane) loader.load();
            
            RootLayoutController cont = loader.getController();
            
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(true);
            stage.show();

            cont.loadEntryDelete(id);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
