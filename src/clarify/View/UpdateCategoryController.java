/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clarify.View;

import clarify.Model.Category;
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
import javafx.scene.control.Button;
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
public class UpdateCategoryController implements Initializable {

    private PageSwitchHelper p = new PageSwitchHelper();
    private Database d = new Database();
    private int id;
    private Category cat;
    private String original;
    
    public void setId(int id){
        this.id = id;
    }
    
    public int getId(){
        return this.id;
    }
    
    public Category getCat() {
        return this.cat;
    }
    
    public void setCat(Category cat) {
        this.cat = cat;
    }
    
    public String getOriginal() {
        return this.original;
    }
    
    public void setOriginal(String original) {
        this.original = original;
    }
    
    public void retrieveCategoryFromDb() throws SQLException{
        String retrieve = "SELECT * FROM Categories "
                + "WHERE cat_id = '"+getId()+"';";
        ResultSet rs = d.getResultSet(retrieve);
        original = rs.getString(2);
        setCat(new Category(rs.getInt(1),rs.getString(2), rs.getString(3)));
        changeFields();
    }
    
    public void changeFields() {
        this.nameField.setText(cat.getName().getValue());
        colourField.setValue(Color.web(cat.getString(cat.getColourRGB())));
    }
    
    
    
    
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
    @FXML
    private Label success1;
    
    @FXML
    private Button btn;
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
    
    public void handleUpdateButton(ActionEvent event) throws SQLException {
        if(checkError() == true){
            String name = nameField.getText();
            String rawColour = colourField.getValue().toString();
            String colour = "#"+rawColour.substring(2, 8);
            try{
                String update = "UPDATE Categories "
                        + "SET category_name = '"+name+"', "
                        + "colour = '"+colour+"' "
                        + "WHERE cat_id = '"+getId()+"';";
                System.out.println(update);
                d.insertStatement(update);
                gridPane.setVisible(false);
                success.setVisible(true);
                success1.setVisible(true);
                btn.setVisible(false);
                System.out.println("Category updated!");
            }
            catch(Exception e){
                e.printStackTrace();
                System.out.println("Category couldn't be updated!!!");
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
        
        if(!nameField.getText().equals(original)){
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
            } catch (SQLException e) {
                System.out.println("Error check failed");
            }
        }
        return errorStatus;
    }
    
}
