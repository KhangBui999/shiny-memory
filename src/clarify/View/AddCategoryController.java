/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clarify.View;

import clarify.Util.Database;
import clarify.Util.PageSwitchHelper;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author khang
 */
public class AddCategoryController implements Initializable {

    PageSwitchHelper p = new PageSwitchHelper();
    Database d = new Database();
    
    @FXML
    private GridPane gridPane;
    
    @FXML
    private TextField nameField;
    @FXML
    private Label nameError;
    
    @FXML
    private ColorPicker colourField;
    
    @FXML
    private Label error;
    @FXML
    private Label success;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void handleBackButton(ActionEvent event) throws IOException {
        p.changeCenter(event, "TimeLogging.fxml");
    }
    
    public void handleCreateButton(ActionEvent event) throws SQLException {
        System.out.println(checkError());
        if(checkError() == true){
            String name = nameField.getText();
            String rawColour = colourField.getValue().toString();
            String colour = "#"+rawColour.substring(2, 8);
            try{
                String insert = "INSERT INTO Categories (category_name, colour) "
                        + "VALUES('"+name+"', '"+colour+"');";
                d.insertStatement(insert);
                gridPane.setVisible(false);
                success.setVisible(true);
                System.out.println("New category created!");
            }
            catch(SQLException e){
                System.out.println("Category couldn't be created!!!");
                error.setVisible(true);
            }
        }
    }
    
    public boolean checkError() {
        boolean errorStatus = true;
        
        if(nameField.getText() == null || nameField.getText().equals("")) {
            nameError.setText("Error: No input detected!");
            nameError.setVisible(true);
            errorStatus = false;
        } else {
            nameError.setVisible(false);
        }
        
        try{
            String statement = "SELECT category_name FROM Categories;";
            ResultSet rs = d.getResultSet(statement);
            while(rs.next()){
                if(nameField.getText().equals(rs.getString(1))){
                    errorStatus = false;
                    nameError.setText("Error: Duplicate categories cannot be created!");
                    nameError.setVisible(true);
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Error check failed");
        }
        return errorStatus;
    }
    
}
